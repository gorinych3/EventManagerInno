package ru.inno.projects.models;

import lombok.*;
import org.hibernate.annotations.Cascade;

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

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "master_team_id")
    private Team master;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "slave_team_id")
    private Team slave;

    @ManyToOne
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.REFRESH,
            org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name="action_id")
    private Action action;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayAction that = (PlayAction) o;
        return playActionId == that.playActionId
                && Objects.equals(master, that.master)
                && Objects.equals(slave, that.slave)
                && Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playActionId, master, slave, action);
    }
}
