package ru.yauroff.test.fileloader.repository.impl;

import ru.yauroff.test.fileloader.model.User;
import ru.yauroff.test.fileloader.repository.IUserRepository;

public class UserRepository extends Repository<Long, User> implements IUserRepository {

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
