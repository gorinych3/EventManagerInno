package ru.inno.projects.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.projects.models.Action;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Invitation;
import ru.inno.projects.models.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findEventsByUsers(User user);

    Event findEventByAction(Action action);

    List<Event> findEventsByInvitations(Invitation invitation);

    List<Event> findEventsByCreateDateBefore(LocalDateTime localDateTime);

    List<Event> findEventsByOwnerUser(User user);
}
