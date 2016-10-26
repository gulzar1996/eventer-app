package com.eventer.app.RecyclerEvent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Gulzar on 26-10-2016.
 */
public abstract class EventListFragment extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Event, EventListViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_event_recyclerlist, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);

        mAdapter = new FirebaseRecyclerAdapter<Event, EventListViewHolder>(Event.class, R.layout.event_card_view_alternative,
                EventListViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(EventListViewHolder viewHolder, Event model, int position)
            {
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
//                        // Need to write to both places the post is stored
//                        DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
//                        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());
//                        // Run two transactions
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
