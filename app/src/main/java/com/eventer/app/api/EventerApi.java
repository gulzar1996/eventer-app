package com.eventer.app.api;

import com.eventer.app.common.Bindable;
import com.eventer.app.model.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gulzar on 24-10-2016.
 */
public class EventerApi {
    DatabaseReference mDatabase;
    Query query;
    public void APIcall(final Bindable<List<Event>> bindable)
    {
        //Demo!!
        mDatabase=FirebaseDatabase.getInstance().getReference("events");
        query=mDatabase.orderByChild("timestamp");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Event> list=new ArrayList<Event>();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Event e=ds.getValue(Event.class);
                    list.add(e);
                }
                bindable.bind(list);
            }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
