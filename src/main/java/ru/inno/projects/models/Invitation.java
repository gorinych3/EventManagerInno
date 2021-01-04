package ru.inno.projects.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "invitations")
@EqualsAndHashCode
public class Invitation {

    @Id
    @GeneratedValue
    private long invitationId;

    private LocalDateTime creationDate;

    private Boolean accepted;

    private Boolean informed;

    // Много приглашений может соответствовать одному событию
    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

    // Много приглашений может соответствовать одному пригласителю
    @ManyToOne
    @JoinColumn(name="invited_user_id")
    private User invitedUser;

    // Много приглашений может соответствовать одному приглашаемому
    @ManyToOne
    @JoinColumn(name="invitor_user_id", nullable=false)
    private User invitorUser;


}
