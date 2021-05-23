package ru.yauroff.test.fileloader.repository.impl;

import ru.yauroff.test.fileloader.model.Event;
import ru.yauroff.test.fileloader.repository.IEventRepository;

public class EventRepository extends Repository<Long, Event> implements IEventRepository {

    @Override
    protected Class<Event> getEntityClass() {
        return Event.class;
    }

    @Override
    protected Long getEntityId(Event entity) {
        return entity.getId();
    }

    @Override
    protected void updateEntity(Event entityFromDB, Event entity) {

    }
}
