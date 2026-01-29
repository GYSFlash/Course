package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Booking;
import com.hotel.model.Client;
import com.hotel.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class BookingRepository extends BaseRepository<Booking,Long> {

    private final String FIND_BY_ID = "SELECT * FROM booking WHERE id = ?;";
    private final String FIND_ALL = "SELECT * FROM booking;";
    private final String CREATE = "INSERT INTO booking (checkInDate, checkOutDate, totalPrice, roomNumber, id_client) " + "VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE booking SET checkInDate = ?, checkOutDate = ?, totalPrice = ?, roomNumber = ?, id_client = ? " + "WHERE id = ?";
    private final String DELETE = "DELETE FROM booking WHERE id = ?;";
    private final String ThreeBookingByRoom = "SELECT * FROM booking WHERE roomNumber = ? ORDER BY id DESC LIMIT 3;";
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

    public List<Booking> threeBookingByRoom(int roomNumber) {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(ThreeBookingByRoom)){
            ps.setInt(1, roomNumber);

            try(ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    bookings.add(mapRow(rs));
                }
            }
            return bookings;
        } catch (SQLException e) {
            System.out.println("Ошибка при выводе трех последних бронирований");
            return null;
        }
    }
    public boolean deleteByClientId(Long id) {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(DELETE_BY_CLIENT_ID)){

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении бронирования");
            return false;
        }
    }
    public boolean deleteByRoomNumber(int roomNumber) {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(DELETE_BY_ROOM_NUMBER)){
            ps.setInt(1, roomNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении бронирования");
            return false;
        }
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
