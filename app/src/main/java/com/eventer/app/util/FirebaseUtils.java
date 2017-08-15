package com.eventer.app.util;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by gulza on 7/4/2017.
 */

public class FirebaseUtils {
    public static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
