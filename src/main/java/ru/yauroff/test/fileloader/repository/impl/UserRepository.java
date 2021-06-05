package ru.yauroff.test.fileloader.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.yauroff.test.fileloader.model.User;
import ru.yauroff.test.fileloader.repository.IUserRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class UserRepository extends Repository<Long, User> implements IUserRepository {

    public Optional<User> findByLogin(String login) {
        Session session = ObjectRepository.getInstance().getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User where login = :login");
        query.setParameter("login", login);
        List users = query.list();
        User entity = !users.isEmpty() ? (User) users.get(0) : null;
        transaction.commit();
        session.close();
        return Optional.of(entity);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Long getEntityId(User entity) {
        return entity.getId();
    }

    @Override
    protected void updateEntity(User entityFromDB, User entity) {
        if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
            entityFromDB.setPassword(entity.getPassword());
        }
        if (entity.getFirstName() != null && !entity.getFirstName().isEmpty()) {
            entityFromDB.setFirstName(entity.getFirstName());
        }
        if (entity.getLastName() != null && !entity.getLastName().isEmpty()) {
            entityFromDB.setLastName(entity.getLastName());
        }
    }
}
