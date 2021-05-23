package ru.yauroff.test.fileloader.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EventController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(EventController.class);

    public void init() throws ServletException {
        logger.info("Into EventController.init");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter messageWriter = response.getWriter();
        messageWriter.println("EventController");
    }

    public void destroy() {
        logger.info("Into EventController.destroy");
    }
}
