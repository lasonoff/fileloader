package ru.yauroff.test.fileloader.service;

import java.util.List;

public interface IService<T, ID> {

    List<T> getAll();

    default T getById(ID id) {
        return null;
    }

    default T create(T entity) throws ParamsNotValidException {
        return null;
    }

    default T update(T entity) {
        return null;
    }

    default void deleteById(ID id) {

    }

    default void deleteAll() {

    }

    default long getCount() {
        return 0L;
    }
}
