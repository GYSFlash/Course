package org.kafka.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kafka.entity.Account;
import org.kafka.entity.Transfer;
import org.kafka.repository.AccountRepository;
import org.kafka.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final Logger logger = LogManager.getLogger(TransferService.class);
    private TransferRepository transferRepository;
    private AccountRepository accountRepository;

    public TransferService(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }
    @Transactional
    public void newTransfer(Transfer transfer) {
        Account account = accountRepository.findById(transfer.getIdFrom()).orElse(null);
        Account accountTo = accountRepository.findById(transfer.getIdTo()).orElse(null);
        if(account != null && accountTo != null) {
            int result = account.getBalance().compareTo(transfer.getAmount());
            if (result >= 0) {
                    account.setBalance(account.getBalance().subtract(transfer.getAmount()));
                    accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));
                    accountRepository.update(account);
                    accountRepository.update(accountTo);
                    transfer.setStatus(Transfer.TranserStatus.DONE);
                    logger.info("Transfer успешен");
                    transferRepository.save(transfer);
            }
            else {transfer.setStatus(Transfer.TranserStatus.ERROR);
            logger.error("Transfer не успешен");
            transferRepository.save(transfer);
        }}
        else {
            logger.error("Transfer не успешен, нет аккаунтов");
            transfer.setStatus(Transfer.TranserStatus.ERROR);
            transferRepository.save(transfer);
        }
    }
}

