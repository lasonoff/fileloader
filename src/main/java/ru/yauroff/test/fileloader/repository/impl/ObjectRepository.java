package ru.yauroff.test.fileloader.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.yauroff.test.fileloader.repository.IEventRepository;
import ru.yauroff.test.fileloader.repository.IFileRepository;
import ru.yauroff.test.fileloader.repository.IUserRepository;

public class ObjectRepository {
    private static final Logger logger = LogManager.getLogger(ObjectRepository.class);

    private static ObjectRepository instance;
    private static SessionFactory sessionFactory;
    private IEventRepository eventRepository;
    private IFileRepository fileRepository;
    private IUserRepository userRepository;

    private ObjectRepository() {
        createSessionFactory();
        userRepository = new UserRepository();
        eventRepository = new EventRepository();
        fileRepository = new FileRepository();
    }

    public IEventRepository getEventRepository() {
        return eventRepository;
    }

    public IFileRepository getFileRepository() {
        return fileRepository;
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static synchronized ObjectRepository getInstance() {
        if (instance == null) {
            instance = new ObjectRepository();
        }
        return instance;
    }

    private void createSessionFactory() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            logger.error("Ошибка при создании SessionFactory!", e);
            System.exit(0);
        }
    }
}
