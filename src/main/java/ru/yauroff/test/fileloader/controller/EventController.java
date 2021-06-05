package ru.yauroff.test.fileloader.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.model.Event;
import ru.yauroff.test.fileloader.service.impl.EventService;
import ru.yauroff.test.fileloader.service.impl.ServiceRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EventController extends BaseController {
    private static final Logger logger = LogManager.getLogger(EventController.class);
    private static EventService eventService;

    @Override
    public void init() throws ServletException {
        eventService = ServiceRepository.getInstance().getEventService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(request.getRequestURI());
        if (request.getRequestURI().equals("/events/count")) {
            response.setContentType("text/html");
            PrintWriter messageWriter = response.getWriter();
            messageWriter.println(eventService.getCount());
            return;
        }
        Long id = parseId(request.getRequestURI(), "events");
        Event event = eventService.getById(id);
        writeJsonToResponse(event, response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Long id = parseId(request.getRequestURI(), "events");
        eventService.deleteById(id);
    }
}
