package com.hotel.repository;

import com.hotel.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository extends BaseRepository<Room, Integer> {
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

    public int countFreeRoom() {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(COUNT_FREE_ROOMS);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете свободных номеров");
            return 0;
        }
    }
    public List<Room> findByStatus(Room.Status status) {
        List<Room> rooms = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(FIND_BY_STATUS)) {

            ps.setString(1, status.name());
            try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rooms.add(mapRow(rs));
            }
            }
            return rooms;
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете свободных номеров");
            return null;
        }
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
