package ru.yauroff.test.fileloader.service;

import java.util.List;

public interface IService<T, ID> {

    List<T> getAll();

    T getById(ID id);

    T create(T entity) throws ParamsNotValidException;

    T update(T entity);

    void deleteById(ID id);

    long getCount();
}
