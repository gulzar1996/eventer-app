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
    @BindView(R.id.ic_event_logo)ImageView mic_event_logo;
    @BindView(R.id.imageview_shot) ImageView mShotImageView;
    @BindView(R.id.textview_date) TextView mDateTextView;
   // @BindView(R.id.textview_likes_count) TextView mLikesTextView;
    @BindView(R.id.textview_attending_count) TextView mAttendingTextView;
    @BindView(R.id.textview_first_prize) TextView mFirstPrizeTextView;
    @BindView(R.id.textview_event_name) TextView mtextview_event_name;
    @BindView(R.id.organizationName)TextView morganizationName;
    @BindView(R.id.event_card_click) View mevent_card_click;
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
        mFirstPrizeTextView.setText(event.prize);
        morganizationName.setText(event.organizationName);
        Glide.with(mic_event_logo.getContext())
                .load(event.logoURL)
                .placeholder(R.drawable.gradient_vertical)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mic_event_logo);
//        starView.setOnClickListener(clickxxx);
            mevent_card_click.setOnClickListener(clickxxx);
             mic_event_logo.setOnClickListener(clickxxx);
            morganizationName.setOnClickListener(clickxxx);
    }
}
