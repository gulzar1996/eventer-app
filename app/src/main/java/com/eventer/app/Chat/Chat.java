package com.eventer.app.Chat;

/**
 * Created by Gulzar on 26-10-2016.
 */
public class Chat {

    private String message;
    private String author;

    // Default constructor is required for Firebase object mapping
    @SuppressWarnings("unuser")
    public Chat(){}
    public Chat(String message, String author) {
        this.message = message;
        this.author = author;
    }
    public String getMessage() {
        return message;
    }
    public String getAuthor() {
        return author;
    }
}
