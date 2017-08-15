package com.eventer.app.RecyclerEvent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.Event.EventActivity;

import com.eventer.app.R;
import com.eventer.app.Story.StoryActivity;
import com.eventer.app.model.Event;
import com.eventer.app.model.Story;
import com.eventer.app.util.FirebaseUtils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

/**
 * Created by Gulzar on 26-10-2016.
 */
public abstract class EventListFragment extends Fragment  {
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Event, EventListViewHolder> mAdapter;
    private FirebaseRecyclerAdapter<Story, StoryListViewHolder> mStoryAdapter;
    public RecyclerView mRecycler,mStoryRecycler;
    private LinearLayoutManager mManager,mStoryLayoutManager;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_event_recyclerlist_story, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseUtils.getDatabase().getReference();
        mDatabase.keepSynced(true);
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.messages_list);
        mStoryRecycler= (RecyclerView) rootView.findViewById(R.id.story_list);

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLayoutManager(mManager);

        //Setup Story Recycler
        mStoryLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mStoryRecycler.setLayoutManager(mStoryLayoutManager);
        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(mStoryRecycler);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getEventQuery(mDatabase);
        Query storyQuery = getStoryQuery(mDatabase);
        setAllEventAdapter(postsQuery);
        setStoryAdapter(storyQuery);
    }

    private void setStoryAdapter(Query storyQuery) {
        mStoryAdapter = new FirebaseRecyclerAdapter<Story, StoryListViewHolder>(Story.class, R.layout.story_icon,
                StoryListViewHolder.class, storyQuery) {
            @Override
            protected void populateViewHolder(StoryListViewHolder viewHolder, final Story model, final int position)
            {
                final DatabaseReference eventRef = getRef(position);
                final String eventKey = eventRef.getKey();
                viewHolder.bindToStory(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(view.getId()==R.id.story_bitmap) {
                            Intent intent = new Intent(getActivity(), StoryActivity.class);
                            intent.putExtra("eid", eventKey);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("EXTRA_STORY", Parcels.wrap(model));
                            intent.putExtras(bundle);
                            Pair<View, String> pair1 = Pair.create(view.findViewById(R.id.story_bitmap), "video_story");
                            ActivityOptionsCompat options = ActivityOptionsCompat.
                                    makeSceneTransitionAnimation(getActivity(),pair1);
                            startActivity(intent, options.toBundle());
                        }
                    }
                });
            }
        };
        mStoryRecycler.setAdapter(mStoryAdapter);
    }

    private void setAllEventAdapter(Query postsQuery) {
        mAdapter = new FirebaseRecyclerAdapter<Event, EventListViewHolder>(Event.class, R.layout.event_card_view_alternative,
                EventListViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(EventListViewHolder viewHolder, final Event model, int position)
            {
                final DatabaseReference eventRef = getRef(position);
                final String eventKey = eventRef.getKey();


                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(view.getId()==R.id.event_card_click)
                        {
                            Intent intent = new Intent(getActivity(), EventActivity.class);
                            intent.putExtra("eid", eventKey);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("EXTRA_EVENT", Parcels.wrap(model));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        if(view.getId()==R.id.organizationName || view.getId()==R.id.ic_event_logo || view.getId()==R.id.topCard)
                        {
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
        };
        mRecycler.setAdapter(mAdapter);
    }

    public abstract Query getEventQuery(DatabaseReference databaseReference);
    public abstract Query getStoryQuery(DatabaseReference databaseReference);

}
