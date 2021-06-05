package ru.yauroff.test.fileloader.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceRepository {
    private static final Logger logger = LogManager.getLogger(ServiceRepository.class);

    private static ServiceRepository instance;
    private UserService userService;
    private FileService fileService;
    private EventService eventService;

    private ServiceRepository() {
        userService = new UserService();
        fileService = new FileService();
        eventService = new EventService();
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

    public FileService getFileService() {
        return fileService;
    }

    public EventService getEventService() {
        return eventService;
    }
}
