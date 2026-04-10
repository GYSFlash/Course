package com.hotel.repository;

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
public class RoomRepository extends BaseRepository<Room, Integer> {

    private static final Logger logger = LogManager.getLogger(RoomRepository.class);


    @Override
    protected Integer getId(Room room) {
        return room.getRoomNumber();
    }
    public RoomRepository(SessionFactory sessionFactory) {
        super(Room.class, sessionFactory);
    }

    public int countFreeRoom() {
        return getSession().createQuery("""
                                    select count(*) from Room
                                    """
        ).getSingleResult().hashCode();
    }
    public List<Room> findByStatus(Room.Status status) {
        List<Room> rooms;
        rooms =  getSession().createQuery("""
                        select r from Room r where r.status = :status
                        """
                ).setParameter("status", status).getResultList();
        return rooms;
    }

}
