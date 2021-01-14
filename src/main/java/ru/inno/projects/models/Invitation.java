package ru.inno.projects.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private String emailInvitation;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "invited_user_id")
    private User invitedUser;

    @ManyToOne
    @JoinColumn(name = "invitor_user_id", nullable = false)
    private User invitorUser;


}
