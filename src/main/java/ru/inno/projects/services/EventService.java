package ru.inno.projects.services;

import ru.inno.projects.models.Action;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.User;

import java.util.List;
import java.util.Set;

public interface EventService {

    List<Event> getAllEvents();

    List<Event> getEventsByUser(User user);

    Event getEventById(long eventId);

    Event getEventByEventName(String eventName);

    boolean addEvent(Event event, Set<User> users);

    boolean addEvent(Event event, Set<User> users, Action action);

    boolean createTeams(long eventId);

    boolean createTeams(List<User> users);

    boolean createRounds(String action);
}
