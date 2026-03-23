package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository extends BaseRepository<Booking,Long> {
    private static final Logger logger = LogManager.getLogger(BookingRepository.class);
    @InjectByType
    private ClientRepository clientRepository;

    @InjectByType
    private RoomRepository roomRepository;


    public BookingRepository(ClientRepository clientRepository, RoomRepository roomRepository, SessionFactory sessionFactory) {
        super(Booking.class, sessionFactory);
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    protected Long getId(Booking booking) {
        return booking.getId();
    }
    @Override
    public List<Booking> findAll() {
        return getSession().createQuery("""
                        select b from Booking b 
                        join fetch b.room 
                        join fetch b.client
                        """
                        ,Booking.class)
                .getResultList();
    }
    public List<Booking> threeBookingByRoom(int roomNumber) {
            return getSession().createQuery(
                            """
                            from Booking b
                            join fetch b.client
                            join fetch b.room
                            where b.room.roomNumber = :roomNumber
                            order by b.id desc
                            """,
                            Booking.class
                    )
                    .setParameter("roomNumber", roomNumber)
                    .setMaxResults(3)
                    .getResultList();
    }

    public boolean deleteByClientId(Long id) {
            int deleted = getSession().createQuery(
                            """
                            delete from Booking b where b.client.id = :id
                           """
                    )
                    .setParameter("id", id)
                    .executeUpdate();
            return deleted > 0;
    }
    public boolean deleteByRoomNumber(int roomNumber) {
        int deleted = getSession().createQuery(
                        """
                           delete from Booking b where b.room.roomNumber = :id
                           """
                )
                .setParameter("id", roomNumber)
                .executeUpdate();
        return deleted > 0;
    }
}
