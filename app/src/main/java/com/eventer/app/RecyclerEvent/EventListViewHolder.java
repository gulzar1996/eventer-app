package com.eventer.app.RecyclerEvent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.R;
import com.eventer.app.model.Event;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gulzar on 26-10-2016.
 */
public class EventListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageview_shot) ImageView mShotImageView;
    @BindView(R.id.textview_date) TextView mDateTextView;
   // @BindView(R.id.textview_likes_count) TextView mLikesTextView;
    @BindView(R.id.textview_attending_count) TextView mAttendingTextView;
    @BindView(R.id.textview_first_prize) TextView mFirstPrizeTextView;
    @BindView(R.id.textview_event_name) TextView mtextview_event_name;
   // @BindView(R.id.sub_text)TextView msub_text;
    public EventListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindToPost(Event event, View.OnClickListener clickxxx) {
        mtextview_event_name.setText(event.title+"");
        mDateTextView.setText(event.date_time);
        if(event.downloadURL!=null)
            Glide.with(mShotImageView.getContext())
                    .load(event.downloadURL)
                    .placeholder(R.drawable.grid_item_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mShotImageView);
        mAttendingTextView.setText(event.registerCount+"");
//        starView.setOnClickListener(clickxxx);
             mtextview_event_name.setOnClickListener(clickxxx);
    }
}
