package ru.inno.projects.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.projects.models.Action;
import ru.inno.projects.models.PlayAction;

import java.util.List;

@Repository
public interface PlayActionRepo extends JpaRepository<PlayAction, Long> {

    List<PlayAction> findAllPlayActionsByAction(Action action);
}
