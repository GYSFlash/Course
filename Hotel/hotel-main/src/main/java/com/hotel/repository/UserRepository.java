package com.hotel.repository;

import com.hotel.model.Booking;
import com.hotel.model.Room;
import com.hotel.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User,Long>{
    private ClientRepository clientRepository;

    public UserRepository(ClientRepository clientRepository, SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
        this.clientRepository = clientRepository;
    }
    @Override
    protected Long getId(User user) {
        return user.getId();
    }

    public Optional<User> findByUsername(String username) {
        return getSession().createQuery("""
                        select u from User u 
                        join fetch u.client
                        where u.username = :username
                        """, User.class
        ).setParameter("username", username).getResultList().stream().findFirst();
    }

}
