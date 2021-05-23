package ru.yauroff.test.fileloader.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


abstract public class Repository<ID, T> {
    private static final Logger logger = LogManager.getLogger(Repository.class);

    public long count() {
        Session session = ObjectRepository.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT count(*) FROM " + getEntityClass().getName());
        Long count = (Long) query.uniqueResult();
        session.close();
        return count;
    }

    public List<T> findAll() {
        Session session = ObjectRepository.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        List<T> entities = session.createQuery("FROM " + getEntityClass().getName()).list();
        session.close();
        return entities;
    }

    public Optional<T> findById(ID id) {
        Session session = ObjectRepository.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        T entity = (T) session.get(getEntityClass(), (Serializable) id);
        transaction.commit();
        session.close();
        return Optional.of(entity);
    }

    public List<T> findAllById(List<ID> ids) {
        return null;
    }

    public T create(T entity) {
        Session session = ObjectRepository.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        ID id = (ID) session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }


    public T update(T entity) {
        Session session = ObjectRepository.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        T entityFromDB = (T) session.get(getEntityClass(), (Serializable) getEntityId(entity));
        updateEntity(entityFromDB, entity);
        session.update(entityFromDB);
        transaction.commit();
        session.close();
        return entityFromDB;
    }

    public List<T> updateAll(List<T> entities) {
        List<T> updatedEntity = entities.stream().map(entity -> update(entity)).filter(Objects::nonNull)
                .collect(Collectors.toList());
        return updatedEntity;
    }

    public void delete(T entity) {
        deleteById(getEntityId(entity));
    }

    public void deleteAll() {
        Session session = ObjectRepository.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from " + getEntityClass().getName());
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    public void deleteById(ID id) {
        Session session = ObjectRepository.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        T entity = (T) session.get(getEntityClass(), (Serializable) id);
        session.delete(entity);
        transaction.commit();
        session.close();
    }

    protected abstract Class<T> getEntityClass();

    protected abstract ID getEntityId(T entity);

    protected abstract void updateEntity(T entityFromDB, T entity);
}
