package ru.inno.projects.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;


@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long Id;

    private String memberName;

    @NotBlank(message = "Email не может быть пустым.")
    private String email;

    @ManyToOne
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

    public void setEvent(Event event) {
        this.event = event;
    }
}
