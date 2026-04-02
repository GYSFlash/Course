package org.kafka;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.kafka.KafkaProducerConfig;
import org.kafka.entity.Account;
import org.kafka.entity.Transfer;
import org.kafka.repository.AccountRepository;
import org.kafka.service.AccountService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@DependsOn("liquibase")
public class ProducerService {
    private final Logger log = LogManager.getLogger(ProducerService.class);
    private final KafkaTemplate<String, Transfer> kafkaTemplate;
    private AccountRepository accountRepository;
    private Map<UUID, Account> accounts = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();
    private AccountService accountService;

    public ProducerService(KafkaTemplate<String, Transfer> kafkaTemplate,
                                   AccountRepository accountRepository, AccountService accountService) {
        this.kafkaTemplate = kafkaTemplate;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @PostConstruct
    public void init() {
        List<Account> all = accountRepository.findAll();
        if (all.isEmpty()) {
            for (int i = 0; i < 1000; i++) {
                Account a = new Account();
                a.setId(UUID.randomUUID());
                a.setBalance(new BigDecimal("10000.00"));
                accountRepository.save(a);
                accounts.put(a.getId(), a);
            }
        } else {
            accounts = accountService.insertMap();
        }
    }

    @Scheduled(fixedDelay = 200)
    public void generateAndSend() {
        if (accounts.size() < 2) return;

        List<UUID> ids = new ArrayList<>(accounts.keySet());
        UUID from, to;
        do {
            from = ids.get(random.nextInt(ids.size()));
            to = ids.get(random.nextInt(ids.size()));
        } while (from.equals(to));

        BigDecimal amount = new BigDecimal(random.nextInt(500) + 1);
        Transfer transfer = new Transfer();
        transfer.setId(UUID.randomUUID());
        transfer.setIdFrom(from);
        transfer.setIdTo(to);
        transfer.setAmount(amount);

        kafkaTemplate.executeInTransaction(kt -> {
            kt.send(KafkaProducerConfig.TOPIC_NAME, transfer)
                    .whenComplete((SendResult<String, Transfer> res, Throwable ex) -> {
                        if (ex == null) {
                            RecordMetadata m = res.getRecordMetadata();
                            log.info("Producer sent: id=" + transfer.getId() + " partition=" + m.partition() + " offset=" + m.offset());
                        } else {
                            log.error("Producer send error: id=" + transfer.getId() + " ex=" + ex.getMessage());
                        }
                    });
            return null;
        });
    }
}