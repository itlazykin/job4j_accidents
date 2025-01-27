package ru.job4j.accidents.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Integer id;

    @Include
    private String username;

    private String password;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;
}