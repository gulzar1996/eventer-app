package com.eventer.app.Chat;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Gulzar on 26-10-2016.
 */
public class ChatContract {

    public interface View {

        void sendMessage();

        void fireBaseOnChildAdded(DataSnapshot dataSnapshot, String s);
    }

    interface UserActionListener {

        void send();
        void childAdded(DataSnapshot dataSnapshot, String s);
    }
}
