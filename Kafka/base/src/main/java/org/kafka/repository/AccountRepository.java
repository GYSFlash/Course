package org.kafka.repository;

import org.hibernate.SessionFactory;
import org.kafka.entity.Account;
import org.springframework.stereotype.Repository;


import java.util.UUID;


@Repository
public class AccountRepository extends BaseRepository<Account,UUID> {

    public AccountRepository(SessionFactory sessionFactory) {
        super(Account.class,sessionFactory);
    }
    public UUID getId(Account account) {
        return account.getId();
    }
}
