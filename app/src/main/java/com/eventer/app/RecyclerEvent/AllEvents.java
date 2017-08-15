package com.eventer.app.RecyclerEvent;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.eventer.app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Gulzar on 26-10-2016.
 */
public class AllEvents extends EventListFragment  {
    public static Fragment newInstance() {
        return new AllEvents();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mbbar = getActivity().findViewById(R.id.abottom_bar);
    }

    @Override
    public Query getEventQuery(DatabaseReference databaseReference) {
        return databaseReference.child("events").orderByChild("timestamp");
    }

    @Override
    public Query getStoryQuery(DatabaseReference databaseReference) {
        return databaseReference.child("stories").orderByChild("timestamp");
    }

}

