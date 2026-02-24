package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Booking;
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
public class BookingRepository extends BaseRepository<Booking,Long> {
    private static final Logger logger = LogManager.getLogger(BookingRepository.class);
    private final String FIND_BY_ID = "SELECT * FROM booking WHERE id = ?;";
    private final String FIND_ALL = "SELECT * FROM booking;";
    private final String CREATE = "INSERT INTO booking (checkInDate, checkOutDate, totalPrice, roomNumber, id_client) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE booking SET checkInDate = ?, checkOutDate = ?, totalPrice = ?, roomNumber = ?, id_client = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM booking WHERE id = ?;";
    private final String THREE_BOOKING_BY_ROOM = "SELECT * FROM booking WHERE roomNumber = ? ORDER BY id DESC LIMIT 3;";
    private final String DELETE_BY_CLIENT_ID = "DELETE FROM booking WHERE id_client = ?;";
    private final String DELETE_BY_ROOM_NUMBER = "DELETE FROM booking WHERE roomNumber = ?;";
    @InjectByType
    private ClientRepository clientRepository;

    @InjectByType
    private RoomRepository roomRepository;
    @Override
    protected String getFindByIdQuery(){
        return FIND_BY_ID;
    }

    public BookingRepository(ClientRepository clientRepository, RoomRepository roomRepository) {
        super(Booking.class);
        this.clientRepository = clientRepository;
        this.roomRepository = roomRepository;
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
    protected Long getId(Booking booking) {
        return booking.getId();
    }
    @Override
    public List<Booking> findAll() {
        Session session = HibernateUtil.getSession();
        return session.createQuery("select b from Booking b " +
                        "join fetch b.room " +
                        "join fetch b.client",
                Booking.class)
                .getResultList();
    }
    public List<Booking> threeBookingByRoom(int roomNumber) {
        Session session = HibernateUtil.getSession();
            return session.createQuery(
                            """
                            from Booking b
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
        Session session = HibernateUtil.getSession();
            int deleted = session.createQuery(
                            "delete from Booking b where b.client.id = :id"
                    )
                    .setParameter("id", id)
                    .executeUpdate();
            return deleted > 0;
    }
    public boolean deleteByRoomNumber(int roomNumber) {
        Session session = HibernateUtil.getSession();
        int deleted = session.createQuery(
                        "delete from Booking b where b.room.roomNumber = :id"
                )
                .setParameter("id", roomNumber)
                .executeUpdate();
        return deleted > 0;
    }
    @Override
    protected Booking mapRow(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getLong("id"));
        booking.setCheckInDate(rs.getDate("checkInDate"));
        booking.setCheckOutDate(rs.getDate("checkOutDate"));
        booking.setTotalPrice(rs.getBigDecimal("totalPrice"));
        Client client = clientRepository.findById(rs.getLong("id_client")).orElse(null);;
        Room room = roomRepository.findById(rs.getInt("roomNumber")).orElse(null);
        booking.setClient(client);
        booking.setRoom(room);
        return booking;
    }
    @Override
    protected void fillInsertStatement(PreparedStatement ps, Booking b) throws SQLException {
        ps.setLong(1, b.getId());
        ps.setDate(2, new java.sql.Date(b.getCheckInDate().getTime()));
        ps.setDate(3, new java.sql.Date(b.getCheckOutDate().getTime()));
        ps.setBigDecimal(4, b.getTotalPrice());
        ps.setLong(5, b.getRoom().getRoomNumber());
        ps.setLong(6, b.getClient().getId());
    }
    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Booking b) throws SQLException {

        ps.setDate(1, new java.sql.Date(b.getCheckInDate().getTime()));
        ps.setDate(2, new java.sql.Date(b.getCheckOutDate().getTime()));
        ps.setBigDecimal(3, b.getTotalPrice());
        ps.setLong(4, b.getRoom().getRoomNumber());
        ps.setLong(5, b.getClient().getId());
        ps.setLong(6, b.getId());
    }
}
