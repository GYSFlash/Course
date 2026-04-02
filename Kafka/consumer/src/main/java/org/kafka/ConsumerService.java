package org.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kafka.entity.Transfer;
import org.kafka.service.TransferService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DependsOn("liquibase")
public class ConsumerService {
    private final Logger log = LogManager.getLogger(ConsumerService.class);
    private TransferService transferService;

    public ConsumerService(TransferService transferService) {
        this.transferService = transferService;
    }

    @KafkaListener(topics = "transfers", containerFactory = "kafkaBatchListenerContainerFactory")
    public void onBatch(List<ConsumerRecord<String, Transfer>> records, Acknowledgment ack) {
        for (ConsumerRecord<String, Transfer> rec : records) {
            Transfer tr = rec.value();
            log.info("Consumer start: id=" + tr.getId() + " partition=" + rec.partition() + " offset=" + rec.offset());
            try {
                transferService.newTransfer(tr);
                log.info("Consumer success: id=" + tr.getId());
            } catch (IllegalArgumentException e) {
                log.error("Validation error: id=" + tr.getId() + " msg=" + e.getMessage());
            } catch (Exception e) {
                log.error("Tx error: id=" + tr.getId() + " msg=" + e.getMessage());
            }
        }
        ack.acknowledge();
    }
}