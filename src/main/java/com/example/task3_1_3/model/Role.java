package com.example.task3_1_3.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    @Override
    public String toString() {
        if (name.startsWith("ROLE")) {
            return name.substring(5);
        }
        return name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
