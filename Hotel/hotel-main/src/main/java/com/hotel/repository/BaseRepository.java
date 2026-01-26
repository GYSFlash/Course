package com.hotel.repository;

import com.hotel.annotations.InjectByType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T,ID> implements  GenericRepository<T,ID> {
    @InjectByType
    protected DBConnection dbConnection;

    protected abstract String getFindByIdQuery();
    protected abstract String getFindAllQuery();
    protected abstract String getCreateQuery();
    protected abstract String getUpdateQuery();
    protected abstract String getDeleteQuery();


    protected abstract void fillInsertStatement(PreparedStatement ps, T entity) throws SQLException;
    protected abstract void fillUpdateStatement(PreparedStatement ps, T entity) throws SQLException;
    protected abstract T mapRow(ResultSet rs) throws SQLException;
    protected abstract Object getId(T entity);

    @Override
    public Optional<T> findById(ID id) {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(getFindByIdQuery())) {

            ps.setObject(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            System.out.println("Ошибка при поиске по id=" + id);
            return Optional.empty();
        }
    }
    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(getFindAllQuery());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            System.out.println("Ошибка при поиске всех клиентов");
            return null;
        }
    }
    @Override
    public T create(T entity) {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(getCreateQuery()))
        {
            fillInsertStatement(ps, entity);
            ps.executeUpdate();
            return entity;

        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении клиента: " + entity);
            return null;
        }
    }
    @Override
    public T update(T entity) {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(getUpdateQuery())) {

            fillUpdateStatement(ps, entity);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                System.out.println("Объект для обновления с id=" + getId(entity) + " не найден");
            }
            return entity;

        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении таблицы: " + entity);
            return null;
        }
    }

    @Override
    public boolean deleteById(ID id) {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(getDeleteQuery())) {

            ps.setObject(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении объекта с id=" + id);
            return false;
        }
    }

}
