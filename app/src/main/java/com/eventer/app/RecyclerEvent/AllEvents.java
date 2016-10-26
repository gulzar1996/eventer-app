package com.eventer.app.RecyclerEvent;

import android.support.v4.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Gulzar on 26-10-2016.
 */
public class AllEvents extends EventListFragment {
    public static Fragment newInstance() {
        return new AllEvents();
    }
    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("events");
    }
}
