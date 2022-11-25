package com.example.BoardGameEventBackend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    @NotNull
    String username;
    @Column(unique = true, nullable = false)
    @NotNull
    @Email
    String email;
    @Column(unique = true, nullable = false)
    @NotNull
    String password;
    @CreatedDate
    @Column(updatable = false)
    LocalDate createdAt;
    @LastModifiedDate
    LocalDate updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public void addRole(Role role){
        roles.add(role);
    }

    public void removeRole(Role role){
        roles.remove(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && email.equals(user.email) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password);
    }
}
