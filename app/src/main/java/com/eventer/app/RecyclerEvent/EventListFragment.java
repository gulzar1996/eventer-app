package com.eventer.app.RecyclerEvent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eventer.app.Event.EventActivity;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

/**
 * Created by Gulzar on 26-10-2016.
 */
public abstract class EventListFragment extends Fragment implements ObservableScrollViewCallbacks  {
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Event, EventListViewHolder> mAdapter;
    public ObservableRecyclerView mRecycler;
    private LinearLayoutManager mManager;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_event_recyclerlist, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (ObservableRecyclerView) rootView.findViewById(R.id.messages_list);
        mRecycler.setScrollViewCallbacks(this);

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
            protected void populateViewHolder(EventListViewHolder viewHolder, final Event model, int position)
            {
                final DatabaseReference eventRef = getRef(position);
                final String eventKey = eventRef.getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch IndividualEventActivity
                        Intent intent = new Intent(getActivity(), EventActivity.class);
                        intent.putExtra("eid", eventKey);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("EXTRA_EVENT", Parcels.wrap(model));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
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


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll,
                                boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
//        if (scrollState == ScrollState.UP) {
//            if () {
//                ab.hide();
//            }
//        } else if (scrollState == ScrollState.DOWN) {
//            if (!ab.isShowing()) {
//                ab.show();
//            }
//        }
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
