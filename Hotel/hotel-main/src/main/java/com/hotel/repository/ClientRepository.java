package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ClientRepository extends BaseRepository<Client, Long> {

    private final String FIND_BY_ID = "SELECT * FROM client WHERE id = ?;";
    private final String FIND_ALL = "SELECT * FROM client;";
    private final String CREATE = "INSERT INTO client (name, surname, dateOfBirth, gender) VALUES (?, ?, ?, ?);";
    private final String UPDATE = "UPDATE client SET name = ?, surname = ?, dateOfBirth = ?, gender = ? WHERE id = ?;";
    private final String DELETE = "DELETE FROM client WHERE id = ?;";
    private final String COUNT = "SELECT COUNT(*) FROM client;";
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
    protected Long getId(Client client) {
        return client.getId();
    }

    public int count() {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(COUNT);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете клиентов");
            return 0;
        }
    }
    @Override
    protected Client mapRow(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setId(rs.getLong("id"));
        client.setName(rs.getString("name"));
        client.setSurname(rs.getString("surname"));
        client.setDateOfBirth(rs.getDate("dateOfBirth"));
        client.setGender(Client.Gender.valueOf(rs.getString("gender")));
        return client;
    }
    @Override
    protected void fillInsertStatement(PreparedStatement ps, Client c) throws SQLException {
        ps.setString(1, c.getName());
        ps.setString(2, c.getSurname());
        ps.setDate(3, new java.sql.Date(c.getDateOfBirth().getTime()));
        ps.setString(4, c.getGender().name());
    }
    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Client c) throws SQLException {
        ps.setString(1, c.getName());
        ps.setString(2, c.getSurname());
        ps.setDate(3, new java.sql.Date(c.getDateOfBirth().getTime()));
        ps.setString(4, c.getGender().name());
        ps.setLong(5, c.getId());
    }
}
