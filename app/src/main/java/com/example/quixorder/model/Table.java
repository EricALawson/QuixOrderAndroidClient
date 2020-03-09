package com.example.quixorder.model;

import com.google.firebase.firestore.DocumentReference;

public class Table extends Account {
    private DocumentReference server;
    private @AccountType String type;
    private String username;
    private String password;

    public Table() {
    }

    public Table(String username, String password) {
        this.server = null;
        this.type = "Table";
        this.username = username;
        this.password = password;
    }

    public DocumentReference getServer() {
        return server;
    }

    public @AccountType String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
