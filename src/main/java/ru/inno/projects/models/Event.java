package ru.inno.projects.models;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "events", schema = "PUBLIC")
public class Event {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long eventId;

    private String eventName;

    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)/*(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})*/
    @Fetch(FetchMode.SUBSELECT)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(
            name = "events_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private Set<User> users = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id")
    private Action action;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventId == event.eventId
                && Objects.equals(eventName, event.eventName)
                && Objects.equals(createDate, event.createDate)
                /*&& Objects.equals(action, event.action)*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventName, createDate/*, action*/);
    }
}
