package ru.inno.projects.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teams", schema = "PUBLIC")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teamId;

    private String teamName;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "teams_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")}
    )
    private Set<User> users = new HashSet<>();

    @OneToOne(mappedBy = "master")
    private PlayAction playActionMaster;

    @OneToOne(mappedBy = "slave")
    private PlayAction playActionSlave;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId == team.teamId
                && Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, teamName);
    }
}
