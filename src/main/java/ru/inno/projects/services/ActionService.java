package ru.inno.projects.services;


import ru.inno.projects.models.Action;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.PlayAction;

import java.util.Set;

public interface ActionService {

    Action getActionByEvent(Event event);

    Event getEventByAction(Action action);

    Set<PlayAction> getAllPlayActionsByAction(Action action);


}
