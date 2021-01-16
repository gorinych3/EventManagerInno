package ru.inno.projects.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "PUBLIC")
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

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Event> events = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "teams_users",
            joinColumns = {@JoinColumn(name = "team_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<Team> teams = new HashSet<>();

    // Один пользователь может иметь несколько приглашений
    @OneToMany(mappedBy = "invitedUser")
    private Set<Invitation> invitationsUserInvited;

    // Один пользователь может создать несколько приглашений
    @OneToMany(mappedBy = "invitorUser")
    private Set<Invitation> invitationsUserInvitor;

    @OneToMany(mappedBy = "ownerUser")
    private Set<Event> usersEvents;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public User(@NotBlank(message = "Имя пользователя не может быть пустым.") @Pattern(regexp = "[A-Za-z0-9._-]*", message = "Имя может быть написано только латиницей и содержать цифры, а также символы ._-.") @Size(min = 1, max = 40, message = "Максимальная длина имени 40 символов.") String username, @NotBlank(message = "Пароль не может быть пустым.") String password, boolean active, @Email(message = "Email не соответствует стандарту.") @NotBlank(message = "Email не может быть пустым.") String email, @Pattern(regexp = "^(?:8|\\+)[0-9\\s.\\/-]{6,20}$", message = "Номер должен начинаться с 8 или с +7.") @NotBlank(message = "Телефонный номер не может быть пустым.") String phoneNumber, String activationCode, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.activationCode = activationCode;
        this.roles = roles;
    }

    public User(@NotBlank(message = "Имя пользователя не может быть пустым.") @Pattern(regexp = "[A-Za-z0-9._-]*", message = "Имя может быть написано только латиницей и содержать цифры, а также символы ._-.") @Size(min = 1, max = 40, message = "Максимальная длина имени 40 символов.") String username, @NotBlank(message = "Пароль не может быть пустым.") String password, boolean active, @Email(message = "Email не соответствует стандарту.") @NotBlank(message = "Email не может быть пустым.") String email, @Pattern(regexp = "^(?:8|\\+)[0-9\\s.\\/-]{6,20}$", message = "Номер должен начинаться с 8 или с +7.") @NotBlank(message = "Телефонный номер не может быть пустым.") String phoneNumber, String activationCode, Set<Event> events, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.activationCode = activationCode;
        this.events = events;
        this.roles = roles;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return active == user.active
                && Objects.equals(userId, user.userId)
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email)
                && Objects.equals(phoneNumber, user.phoneNumber)
                && Objects.equals(activationCode, user.activationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, active, email, phoneNumber, activationCode);
    }
}
