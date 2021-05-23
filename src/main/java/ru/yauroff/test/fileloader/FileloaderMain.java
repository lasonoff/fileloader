package ru.yauroff.test.fileloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.repository.impl.ObjectRepository;
import ru.yauroff.test.fileloader.service.impl.ServiceRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class FileloaderMain extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FileloaderMain.class);

    @Override
    public void init() throws ServletException {
        logger.info("Into FileloaderMain.init");
        ObjectRepository.getInstance();
        logger.info("Init ObjectRepository: Ok!");
        ServiceRepository.getInstance();
        logger.info("Init ServiceRepository: Ok!");
    }
}
