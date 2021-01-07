package ru.inno.projects.models;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude = {"roles"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;

    @NotBlank(message = "Имя пользователя не может быть пустым.")
    @Pattern(regexp = "[A-Za-z0-9._-]*", message = "Имя может быть написано только латиницей и содержать цифры, а также символы ._-.")
    @Size(min = 1, max = 40, message = "Максимальная длина имени 40 символов.")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым.")
    private String password;

    @Transient
    private String password2;

    private boolean active;

    @Email(message = "Email не соответствует стандарту.")
    @NotBlank(message = "Email не может быть пустым.")
    private String email;

    @Pattern(regexp = "^(?:8|\\+)[0-9\\s.\\/-]{6,20}$", message = "Номер должен начинаться с 8 или с +7.")
    @NotBlank(message = "Телефонный номер не может быть пустым.")
    private String phoneNumber;

    private String activationCode;
    @ManyToMany
    @JoinTable(
            name = "events_users",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<Event> events = new HashSet<>();

//    // Один пользователь может иметь несколько приглашений
//    @OneToMany(mappedBy="invitedUser")
//    private Set<Invitation> invitationsUserInvited;
//
//    // Один пользователь может создать несколько приглашений
//    @OneToMany(mappedBy="invitorUser")
//    private Set<Invitation> invitationsUserInvitor;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
