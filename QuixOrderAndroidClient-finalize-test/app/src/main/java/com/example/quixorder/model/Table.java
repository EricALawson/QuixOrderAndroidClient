package com.example.quixorder.model;

public class Table extends Account {
    private final String server;
    private final @AccountType String type;
    private final String username;
    private final String password;

    public Table() {
        server = "";
        type = "";
        username = "";
        password = "";
    }

    public Table(String username, String password) {
        this.server = null;
        this.type = "Table";
        this.username = username;
        this.password = password;
    }

    public String getServer() {
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
