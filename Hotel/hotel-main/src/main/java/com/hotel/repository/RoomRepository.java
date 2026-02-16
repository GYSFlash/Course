package com.hotel.repository;

import com.hotel.model.Client;
import com.hotel.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
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
    private final String FIND_BY_ID = "SELECT * FROM room WHERE roomNumber = ?;";
    private final String FIND_ALL = "SELECT * FROM room;";
    private final String CREATE = "INSERT INTO room (roomNumber, price, place, type, status, stars) VALUES (?,?,?, ?,?, ?);";
    private final String UPDATE = "UPDATE room SET price = ?, place = ?, type = ?, status = ?, stars = ? WHERE roomNumber = ?;";
    private final String DELETE = "DELETE FROM room WHERE roomNumber = ?;";
    private final String COUNT_FREE_ROOMS = "SELECT COUNT(*) FROM room WHERE status = 'FREE';";
    private final String FIND_BY_STATUS = "SELECT * FROM room WHERE status = ?;";
    @Override
    protected String getFindByIdQuery(){
        return FIND_BY_ID;
    }

    @Override
    protected String getFindAllQuery(){
        return FIND_ALL;
    }
    @Override
    protected String getCreateQuery(){
        return CREATE;
    }
    @Override
    protected String getUpdateQuery(){
        return UPDATE;
    }
    @Override
    protected String getDeleteQuery(){
        return DELETE;
    }
    @Override
    protected Integer getId(Room room) {
        return room.getRoomNumber();
    }
    public RoomRepository() {
        super(Room.class);
    }

    public int countFreeRoom() {
        Session session = HibernateUtil.getSession();
        return session.createQuery("select count(*) from Room").getSingleResult().hashCode();
    }
    public List<Room> findByStatus(Room.Status status) {
        List<Room> rooms = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        rooms =  session.createQuery("select r from Room r where r.status = :status")
                .setParameter("status", status).getResultList();
        return rooms;
    }
    @Override
    protected Room mapRow(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomNumber(rs.getInt("roomNumber"));
        room.setPrice(rs.getBigDecimal("price"));
        room.setPlace(rs.getInt("place"));
        room.setType(Room.RoomType.valueOf(rs.getString("type")));
        room.setStatus(Room.Status.valueOf(rs.getString("status")));
        room.setStars(Room.Star.valueOf(rs.getString("stars")));
        return room;
    }
    @Override
    protected void fillInsertStatement(PreparedStatement ps, Room r) throws SQLException {
        ps.setInt(1, r.getRoomNumber());
        ps.setBigDecimal(2, r.getPrice());
        ps.setInt(3, r.getPlace());
        ps.setString(4, r.getType().name());
        ps.setString(5, r.getStatus().name());
        ps.setString(6, r.getStars().name());
    }
    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Room r) throws SQLException {

        ps.setBigDecimal(1, r.getPrice());
        ps.setInt(2, r.getPlace());
        ps.setString(3, r.getType().name());
        ps.setString(4, r.getStatus().name());
        ps.setString(5, r.getStars().name());
        ps.setInt(6, r.getRoomNumber());
    }
}
