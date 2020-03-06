package com.example.quixorder.model;

import android.support.annotation.StringDef;

import java.util.UUID;

public class Account {
    private final @AccountType String type;
    private final String username;
    private final String password;

    //This is supposed to give warnings if the String is anything other that the ones defined here.
    //We could use an Enum, but it will crash if an invalid string is read from Firebase.
    @StringDef({
            COOK,
            OWNER,
            SERVER,
            CUSTOMER
    })
    public @interface AccountType {}
    public static final String COOK = "Cook";
    public static final String OWNER = "Owner";
    public static final String SERVER = "Server";
    public static final String CUSTOMER = "Customer";

    public Account() {
        type = "";
        username = "";
        password = "";
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
