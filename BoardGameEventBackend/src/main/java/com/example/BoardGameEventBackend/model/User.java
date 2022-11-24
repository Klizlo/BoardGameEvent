package com.example.BoardGameEventBackend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    @NotNull
    String username;
    @Column(unique = true, nullable = false)
    @NotNull
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
    private List<Role> roles = new ArrayList<>();


    public void addRole(Role role){
        roles.add(role);
    }

}
