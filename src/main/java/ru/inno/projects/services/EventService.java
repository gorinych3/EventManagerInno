package ru.inno.projects.services;

import ru.inno.projects.models.Event;
import ru.inno.projects.models.User;

import java.util.List;
import java.util.Set;

public interface EventService {

    List<Event> getAllEvents();

    List<Event> getEventsByUser(User user);

    List<Event> getEventsByOwner(User user);

    Event getEventById(long eventId);

    Event startAction(long eventId);

    Set<User> createUserList(int countUser, Event event);

    Event save(Event event, Integer teams, Integer playersOnTeam);

    boolean getBoolResultAction(Event event);
}
