package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T,ID> implements  GenericRepository<T,ID> {

    private static final Logger logger = LogManager.getLogger(BaseRepository.class);
    protected Class<T> entity;
    public BaseRepository(Class<T> entity) {
        this.entity = entity;
    }

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
        Session session = HibernateUtil.getSession();
        return Optional.ofNullable(session.find(entity, id));
    }
    @Override
    public List<T> findAll() {
        Session session = HibernateUtil.getSession();
        return session.createQuery("from " + entity.getSimpleName(), entity).getResultList();
    }
    @Override
    public T create(T entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        }catch (Exception e){
            transaction.rollback();
            logger.error("Ошибка создания объекта");
            return null;
        }
    }
    @Override
    public T update(T entity) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction = session.beginTransaction();
            session.refresh(entity);
            transaction.commit();
            return entity;
        }catch (Exception e){
            transaction.rollback();
            logger.error("Ошибка обновления объекта");
            return null;
        }
    }

    @Override
    public boolean deleteById(ID id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            transaction = session.beginTransaction();
            T entity = findById(id).orElse(null);
            session.remove(entity);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            logger.error("Ошибка при удалении объекта");
            return false;
        }
    }

}
