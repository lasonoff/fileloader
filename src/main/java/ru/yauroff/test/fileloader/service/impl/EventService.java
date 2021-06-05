package ru.yauroff.test.fileloader.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.yauroff.test.fileloader.model.Event;
import ru.yauroff.test.fileloader.repository.impl.EventRepository;
import ru.yauroff.test.fileloader.repository.impl.ObjectRepository;
import ru.yauroff.test.fileloader.service.IService;

import java.util.List;

public class EventService implements IService<Event, Long> {
    private static final Logger logger = LogManager.getLogger(EventService.class);
    private EventRepository eventRepository;

    public EventService() {
        eventRepository = (EventRepository) ObjectRepository.getInstance().getEventRepository();
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public long getCount() {
        return eventRepository.count();
    }

    @Override
    public void deleteAll() {
        eventRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

}
