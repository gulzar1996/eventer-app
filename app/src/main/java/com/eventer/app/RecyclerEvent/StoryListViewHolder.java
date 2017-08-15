package com.eventer.app.RecyclerEvent;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.eventer.app.model.Story;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gulza on 8/15/2017.
 */

public class StoryListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.story_bitmap) CircleImageView mstory_bitmap;
    @BindView(R.id.story_indicator) CircleImageView story_indicator;

    private DatabaseReference mdatabase;
    public StoryListViewHolder(View itemView) {
        super(itemView);
        mdatabase= FirebaseDatabase.getInstance().getReference();
        ButterKnife.bind(this,itemView);
    }
    public void bindToStory(final Story story, View.OnClickListener click) {

        mdatabase.child("watched-stories").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(story.storyKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    story_indicator.setVisibility(View.GONE);
                else
                    story_indicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })
        Glide.with(mstory_bitmap.getContext())
                .load(story.storyUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mstory_bitmap);
        mstory_bitmap.setOnClickListener(click);

    }

}
