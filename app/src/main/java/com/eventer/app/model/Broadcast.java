package com.eventer.app.model;

/**
 * Created by gulza on 11/7/2017.
 */

public class Broadcast {
    public Broadcast(String event, String from, String message, String title) {
        this.event = event;
        this.from = from;
        this.message = message;
        this.title = title;
    }

    public String event;
    public String from;
    public String message;
    public String title;
    public Broadcast() {
    }
}
