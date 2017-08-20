package com.eventer.app.RecyclerEvent;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.eventer.app.model.Story;
import com.eventer.app.util.FirebaseUtils;
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
        mdatabase= FirebaseUtils.getDatabase().getReference();;
        ButterKnife.bind(this,itemView);
    }
    public void bindToStory(final Story story, final View.OnClickListener click) {

        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            mdatabase.child("watched-stories").child(story.storyKey).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).keepSynced(true);
            mdatabase.child("watched-stories").child(story.storyKey).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists())
                        story_indicator.setVisibility(View.GONE);
                    else
                        story_indicator.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        Glide.with(mstory_bitmap.getContext())
                .load(story.storyUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mstory_bitmap.setOnClickListener(click);
                        return false;
                    }
                })
                .error(R.color.circle_bac)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mstory_bitmap);


    }

}
