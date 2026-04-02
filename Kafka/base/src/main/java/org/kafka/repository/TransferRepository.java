package org.kafka.repository;

import org.hibernate.SessionFactory;
import org.kafka.entity.Transfer;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TransferRepository extends BaseRepository<Transfer, UUID> {

    public TransferRepository(SessionFactory sessionFactory) {
        super(Transfer.class, sessionFactory);
    }

    public UUID getId(Transfer transfer) {
        return transfer.getId();
    }

}
