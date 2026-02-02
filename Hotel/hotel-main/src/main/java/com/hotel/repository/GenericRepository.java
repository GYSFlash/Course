package com.hotel.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T,ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T create(T entity);
    T update(T entity);
    boolean deleteById(ID id);

}
