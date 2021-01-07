package ru.inno.projects.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.inno.projects.models.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {
}
