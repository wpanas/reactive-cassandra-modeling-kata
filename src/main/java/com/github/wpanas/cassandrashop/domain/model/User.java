package com.github.wpanas.cassandrashop.domain.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
public class User {
    @PrimaryKey
    private final UUID id;
    private final String login;
    private final int age;

    public User(UUID id, String login, int age) {
        this.id = id;
        this.login = login;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public int getAge() {
        return age;
    }
}
