package com.geteventer.eventer.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.geteventer.eventer.R;
import com.geteventer.eventer.common.Bindable;
import com.geteventer.eventer.model.Event;
import com.geteventer.eventer.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gulzar on 24-10-2016.
 */
    public class EventCardView extends CardView implements Bindable<Event> {

        @BindView(R.id.imageview_shot)
        ImageView mShotImageView;
        @BindView(R.id.textview_shot_name)
        TextView mShotNameTextView;
        @BindView(R.id.textview_location) TextView mUserTextView;
        @BindView(R.id.textview_date) TextView mDateTextView;
        @BindView(R.id.textview_likes_count) TextView mLikesTextView;
        @BindView(R.id.textview_attending_count) TextView mAttendingTextView;
        @BindView(R.id.textview_first_prize) TextView mFirstPrizeTextView;
        public EventCardView(Context context) {
            super(context);
            inflate(context, R.layout.event_card_view, this);
            ButterKnife.bind(this);

            ViewUtils.tintDrawable(mLikesTextView, 0);
            ViewUtils.tintDrawable(mAttendingTextView, 0);
            ViewUtils.tintDrawable(mFirstPrizeTextView, 0);
        }


        @Override
        public void bind(Event event) {
            if(event.downloadURL!=null)
                Glide.with(getContext())
                        .load(event.downloadURL)
                        .placeholder(R.drawable.grid_item_placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(mShotImageView);

            if(event.date_time==null)
                mDateTextView.setVisibility(INVISIBLE);
            else
                mDateTextView.setText(event.date_time);


//                if(event.prizes==null)
//                    mFirstPrizeTextView.setVisibility(GONE);
//                    else
//                    mFirstPrizeTextView.setText(event.prizes.get(0));

            mShotNameTextView.setText(event.title);
        }
    }
