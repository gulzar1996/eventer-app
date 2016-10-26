package com.eventer.app.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaurav on 25/10/16.
 */
public class User {
    public String regno;
    public String name;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String regno,String username) {
        this.name = username;
        this.regno=regno;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("regno", regno);
        result.put("username", name);
        return result;
    }
}
