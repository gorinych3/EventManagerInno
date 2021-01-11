package ru.inno.projects.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "play_actions", schema = "PUBLIC")
public class PlayAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long playActionId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "master_team_id")
    private Team master;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "slave_team_id")
    private Team slave;

    @ManyToOne(fetch = FetchType.LAZY, cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "action_id")
    private Action action;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayAction that = (PlayAction) o;
        return playActionId == that.playActionId
                && Objects.equals(master, that.master)
                && Objects.equals(slave, that.slave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playActionId, master, slave);
    }
}
