package com.eventer.app.RecyclerEvent;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by gaurav on 27/10/16.
 */
public class MyEvents extends MyEventListFragment  {
    public static Fragment newInstance() {
        return new MyEvents();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("events").orderByChild("timestamp");
    }
}

