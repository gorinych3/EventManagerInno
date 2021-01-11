package ru.inno.projects.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.projects.models.Event;
import ru.inno.projects.models.Member;

import java.util.List;
import java.util.Set;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {
    List<Member> findAllMembersByEvent(Event event);
}
