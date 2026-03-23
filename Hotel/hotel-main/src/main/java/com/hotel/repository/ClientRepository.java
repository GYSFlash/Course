package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepository extends BaseRepository<Client, Long> {
    private static final Logger logger = LogManager.getLogger(ClientRepository.class);


    public ClientRepository(SessionFactory sessionFactory) {
        super(Client.class, sessionFactory);
    }

    @Override
    protected Long getId(Client client) {
        return client.getId();
    }

    public int count() {
        return getSession().createQuery("""
                                select count(*) from Client                           
                                """
        ).getSingleResult().hashCode();
    }

}
