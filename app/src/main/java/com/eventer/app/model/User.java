package com.eventer.app.model;

import com.google.firebase.database.Exclude;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaurav on 25/10/16.
 */
@Parcel
public class User {
    public String regno;
    public String name;
    public User() {
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
