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
import java.util.List;

public class EventsController extends BaseController {
    private static final Logger logger = LogManager.getLogger(EventsController.class);
    private static EventService eventService;

    @Override
    public void init() throws ServletException {
        eventService = ServiceRepository.getInstance().getEventService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Event> events = eventService.getAll();
        writeJsonToResponse(events, response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        eventService.deleteAll();
    }
}
