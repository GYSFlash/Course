package org.kafka.service;

import org.kafka.entity.Account;
import org.kafka.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public Map<UUID,Account> insertMap(){
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().collect(Collectors.toMap(Account::getId, account -> account));
    }
    public void createAccount(Account account) {
        accountRepository.save(account);
    }
    public void updateAccount(Account account) {
        accountRepository.update(account);
    }
    public void deleteAccount(Account account) {
        accountRepository.deleteById(account.getId());
    }

}
