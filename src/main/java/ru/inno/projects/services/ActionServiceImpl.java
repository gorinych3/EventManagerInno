package ru.inno.projects.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.projects.models.Action;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.PlayAction;
import ru.inno.projects.repos.ActionRepo;
import ru.inno.projects.repos.EventRepo;
import ru.inno.projects.repos.PlayActionRepo;

import java.util.HashSet;
import java.util.Set;

@Service
public class ActionServiceImpl implements ActionService {

    private final ActionRepo actionRepo;
    private final EventRepo eventRepo;
    private final PlayActionRepo playActionRepo;

    @Autowired
    public ActionServiceImpl(ActionRepo actionRepo, EventRepo eventRepo, PlayActionRepo playActionRepo) {
        this.actionRepo = actionRepo;
        this.eventRepo = eventRepo;
        this.playActionRepo = playActionRepo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Action getActionByEvent(Event event) {
        return actionRepo.findActionByEvent(event);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Action getActionById(long actionId) {
        return actionRepo.findActionByActionId(actionId);
    }

    @Transactional
    @Override
    public Event getEventByAction(Action action) {
        return eventRepo.findEventByAction(action);
    }

    @Transactional
    @Override
    public Set<PlayAction> getAllPlayActionsByAction(Action action) {
        return new HashSet<>(playActionRepo.findAllPlayActionsByAction(action));
    }
}
