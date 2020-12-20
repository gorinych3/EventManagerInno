package ru.inno.projects.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.User;
import ru.inno.projects.repos.EventRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private EventRepo eventRepo;

    @Autowired
    public EventServiceImpl(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    @Transactional
    @Override
    public List<Event> getEventsByUser(User user) {
        log.info("start getEventsByUser in EventServiceImpl");
        return eventRepo.findEventsByUsers(user);
    }

    @Override
    public Event getEventById(long eventId) {
        return eventRepo.getOne(eventId);
    }

    @Override
    public Event getEventByEventName(String eventName) {
        return null;
    }

    @Override
    public boolean addEvent(Event event, Set<User> users) {
        event.setUsers(users);
        eventRepo.save(event);
        return true;
    }
}
