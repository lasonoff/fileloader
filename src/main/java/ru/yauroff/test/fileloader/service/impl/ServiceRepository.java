package ru.yauroff.test.fileloader.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceRepository {
    private static final Logger logger = LogManager.getLogger(ServiceRepository.class);

    private static ServiceRepository instance;
    private UserService userService;

    private ServiceRepository() {
        userService = new UserService();
    }

    public static synchronized ServiceRepository getInstance() {
        if (instance == null) {
            instance = new ServiceRepository();
        }
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }
}
