package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import com.hotel.model.Client;
import com.hotel.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

public class ServiceRepository extends BaseRepository<Service, Long> {
    private final String FIND_BY_ID = "SELECT * FROM service WHERE id = ?;";
    private final String FIND_ALL = "SELECT * FROM service;";
    private final String SAVE = "INSERT INTO service (typeService, serviceName, servicePrice, duration, date, id_client) VALUES (?, ?, ?, ?, ?, ?);";
    private final String UPDATE = "UPDATE service SET typeService = ?, serviceName = ?, servicePrice = ?, duration = ?, date = ?, id_client = ? WHERE id = ?;";
    private final String DELETE = "DELETE FROM service WHERE id = ?;";
    private final String DELETE_BY_CLIENT_ID = "DELETE FROM service WHERE id_client = ?;";
    @InjectByType
    private ClientRepository clientRepository;

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
        return SAVE;
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
    protected Long getId(Service service) {
        return service.getId();
    }

    public boolean deleteByClientId(Long id) {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(DELETE_BY_CLIENT_ID)){

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении услуги");
            return false;
        }
    }
    @Override
    protected Service mapRow(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setId(rs.getLong("id"));
        service.setTypeService(Service.TypeService.valueOf(rs.getString("typeService")));
        service.setServiceName(rs.getString("serviceName"));
        service.setServicePrice(rs.getBigDecimal("servicePrice"));
        service.setDuration(Duration.ofMinutes(rs.getLong("duration")));
        service.setDate(rs.getDate("date"));
        Client client = clientRepository.findById(rs.getLong("id_client")).orElse(null);
        service.setClient(client);
        return service;
    }
    @Override
    protected void fillInsertStatement(PreparedStatement ps, Service s) throws SQLException {
        ps.setString(1, s.getTypeService().name());
        ps.setString(2, s.getServiceName());
        ps.setBigDecimal(3, s.getServicePrice());
        ps.setLong(4, s.getDuration().toMinutes());
        ps.setDate(5, new java.sql.Date(s.getDate().getTime()));
        ps.setLong(6, s.getClient().getId());

    }
    @Override
    protected void fillUpdateStatement(PreparedStatement ps, Service s) throws SQLException {
        ps.setString(1, s.getTypeService().name());
        ps.setString(2, s.getServiceName());
        ps.setBigDecimal(3, s.getServicePrice());
        ps.setLong(4, s.getDuration().toMinutes());
        ps.setDate(5, new java.sql.Date(s.getDate().getTime()));
        ps.setLong(6, s.getClient().getId());
        ps.setLong(7, s.getId());
    }
}
