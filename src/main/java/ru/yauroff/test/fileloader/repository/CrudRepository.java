package ru.yauroff.test.fileloader.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<ID, T> {

    long count();

    T create(T entity);

    void delete(T entity);

    void deleteAll();

    void deleteById(ID id);

    List<T> findAll();

    List<T> findAllById(List<ID> ids);

    Optional<T> findById(ID id);

    T update(T entity);

    List<T> updateAll(List<T> entities);
}
