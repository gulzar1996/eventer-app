package com.eventer.app.RecyclerEvent;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.R;
import com.eventer.app.model.Event;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gulza on 8/15/2017.
 */

public class StoryListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.story_bitmap) CircleImageView mstory_bitmap;
    public StoryListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void bindToStory(Event event, View.OnClickListener click) {
        Glide.with(mstory_bitmap.getContext())
                .load(event.downloadURL)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mstory_bitmap);
        mstory_bitmap.setOnClickListener(click);
    }

}
