package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

@Repository
public class ServiceRepository extends BaseRepository<Service, Long> {
    private static final Logger logger = LogManager.getLogger(ServiceRepository.class);

    private ClientRepository clientRepository;
    @Override
    public List<Service> findAll() {
        Session session = HibernateUtil.getSession();
        return session.createQuery("select s from Service s " +
                                "join fetch s.client",
                        Service.class)
                .getResultList();
    }
    public ServiceRepository(ClientRepository clientRepository, SessionFactory sessionFactory) {
        super(Service.class, sessionFactory);
        this.clientRepository = clientRepository;
    }

    @Override
    protected Long getId(Service service) {
        return service.getId();
    }

    public boolean deleteByClientId(Long id) {
        int deleted = getSession().createQuery("""
                            delete from Service s where s.client.id = :id
                            """
                ).setParameter("id", id).executeUpdate();
        return deleted > 0;
    }
}
