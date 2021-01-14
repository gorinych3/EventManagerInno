package ru.inno.projects.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.projects.models.Action;
import ru.inno.projects.models.Event;

@Repository
public interface ActionRepo extends JpaRepository<Action, Long> {
    Action findActionByEvent(Event event);

}
