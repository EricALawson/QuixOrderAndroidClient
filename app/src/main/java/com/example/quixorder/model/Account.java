package com.example.quixorder.model;

import java.util.UUID;

public class Account {
    private final UUID id;
    private final String type;
    private final String username;
    private final String password;

    public Account(UUID id, String type, String username, String password) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
