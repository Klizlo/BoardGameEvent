package com.example.BoardGameEventBackend.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String username;
    @Column(unique = true, nullable = false)
    String email;
    String password;
    @CreatedDate
    LocalDate createdAt;
    @LastModifiedDate
    LocalDate updatedAt;


}
