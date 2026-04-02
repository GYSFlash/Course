package org.kafka.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public abstract class BaseRepository<T,ID> {

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

    public Optional<T> findById(ID id) {

        return Optional.ofNullable(getSession().find(entity, id));
    }
    public List<T> findAll() {
        return getSession().createQuery("from " + entity.getSimpleName(), entity).getResultList();
    }
    public T save(T entity) {

        try {
            getSession().persist(entity);
            return entity;
        }catch (Exception e){

            return null;
        }
    }
    public T update(T entity) {
        try {
            T updatedEntity = getSession().merge(entity);
            return updatedEntity;
        }catch (Exception e){

            return null;
        }
    }

    public boolean deleteById(ID id) {
        try {
            T entity = findById(id).orElse(null);
            getSession().remove(entity);
            return true;
        }catch (Exception e){

            return false;
        }
    }

}
