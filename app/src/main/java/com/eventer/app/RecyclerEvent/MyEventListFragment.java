package com.eventer.app.RecyclerEvent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.Event.EventActivity;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.eventer.app.util.FirebaseUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

/**
 * Created by gaurav on 27/10/16.
 */
public abstract class MyEventListFragment extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Event, EventListViewHolder> mAdapter;
    public RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    public String uid;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_event_recyclerlist, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseUtils.getDatabase().getReference();
        mDatabase.keepSynced(true);
        // [END create_database_reference]

        // [start get curren user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid=user.getUid();
        } else {

        }
        //[end get current user]


        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);


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
            protected void populateViewHolder(EventListViewHolder viewHolder, final Event model, int position) {
                final DatabaseReference eventRef = getRef(position);
                final String eventKey = eventRef.getKey();

                if (model.userkey.contains(uid) || model.registers.containsKey(uid)) {
                    viewHolder.bindToPost(model, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (view.getId() == R.id.event_card_click) {
                                Intent intent = new Intent(getActivity(), EventActivity.class);
                                intent.putExtra("eid", eventKey);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("EXTRA_EVENT", Parcels.wrap(model));
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                            if (view.getId() == R.id.organizationName || view.getId() == R.id.ic_event_logo) {
                                MaterialDialog md= new MaterialDialog.Builder(getActivity())
                                        .limitIconToDefaultSize()// limits the displayed icon size to 48dp
                                        .icon(getResources().getDrawable(R.drawable.ic_likes))
                                        .title(model.organizationName)
                                        .content(model.organizationDescription)
                                        .show();
                                Glide.with(md.getIconView().getContext())
                                        .load(model.logoURL)
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                        .into((md.getIconView()));
                            }
                        }
                    });
                }
                else
                {
                    viewHolder.removefragment();
                }
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}
