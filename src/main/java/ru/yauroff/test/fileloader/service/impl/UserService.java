package ru.yauroff.test.fileloader.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.model.User;
import ru.yauroff.test.fileloader.repository.IUserRepository;
import ru.yauroff.test.fileloader.repository.impl.ObjectRepository;
import ru.yauroff.test.fileloader.service.IService;
import ru.yauroff.test.fileloader.service.ParamsNotValidException;

import java.util.List;

public class UserService implements IService<User, Long> {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private IUserRepository userRepository;

    public UserService() {
        userRepository = ObjectRepository.getInstance().getUserRepository();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public long getCount() {
        return userRepository.count();
    }

    @Override
    public User create(User user) throws ParamsNotValidException {
        if (user.getId() != null || user.getLogin() == null || user.getLogin().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ParamsNotValidException("Not valid input params for User!");
        }
        User resUser = userRepository.create(user);
        return resUser;
    }

    @Override
    public User update(User user) {
        User resUser = userRepository.update(user);
        return resUser;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
