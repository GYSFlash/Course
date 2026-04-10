package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    protected final SessionFactory sessionFactory;
    public BaseRepository(Class<T> entity, SessionFactory sessionFactory) {
        this.entity = entity;
        this.sessionFactory = sessionFactory;
    }
    protected abstract Object getId(T entity);
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    @Override
    public Optional<T> findById(ID id) {

        return Optional.ofNullable(getSession().find(entity, id));
    }
    @Override
    public List<T> findAll() {
        return getSession().createQuery("from " + entity.getSimpleName(), entity).getResultList();
    }
    @Override
    public T create(T entity) {

        try {
            getSession().persist(entity);
            return entity;
        }catch (Exception e){
            logger.error("Ошибка создания объекта");
            return null;
        }
    }
    @Override
    public T update(T entity) {
        try {
            T updatedEntity = getSession().merge(entity);
            return updatedEntity;
        }catch (Exception e){
            logger.error("Ошибка обновления объекта");
            return null;
        }
    }

    @Override
    public boolean deleteById(ID id) {
        try {
            T entity = findById(id).orElse(null);
            getSession().remove(entity);
            return true;
        }catch (Exception e){
            logger.error("Ошибка при удалении объекта");
            return false;
        }
    }

}
