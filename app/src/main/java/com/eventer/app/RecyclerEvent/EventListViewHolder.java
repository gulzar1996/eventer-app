package com.eventer.app.RecyclerEvent;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.eventer.app.util.ViewUtils;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

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
    @BindView(R.id.topCard) View topCard;
    @BindView(R.id.cardView) FrameLayout mcardView;
    @BindView(R.id.cardViewEvent) LinearLayout mcardViewEvent;
    @BindView(R.id.rootViewCard)
    LinearLayout mrootViewCard;
   // @BindView(R.id.sub_text)TextView msub_text;
    public EventListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindToPost(Event event, View.OnClickListener clickxxx) {
        mrootViewCard.setVisibility(View.VISIBLE);
        mcardViewEvent.setVisibility(View.VISIBLE);
        mcardView.setVisibility(View.VISIBLE);
        mtextview_event_name.setText(event.title+"");
        mDateTextView.setText(event.date_time);

        mFirstPrizeTextView.setCompoundDrawables(new IconicsDrawable(mFirstPrizeTextView.getContext(), FontAwesome.Icon.faw_trophy).sizeDp(16).color(Color.GRAY),null,null,null);
        //Group Or SOLO Icon
        int maxReg=event.maxReg;
        int minReg=event.minReg;
        if(minReg==1 && maxReg==1)
            mAttendingTextView.setCompoundDrawables(new IconicsDrawable(mAttendingTextView.getContext(), FontAwesome.Icon.faw_user).sizeDp(16).color(Color.GRAY),null,null,null);
        else
        if(minReg==maxReg)
            mAttendingTextView.setCompoundDrawables(new IconicsDrawable(mAttendingTextView.getContext(), FontAwesome.Icon.faw_users).sizeDp(16).color(Color.GRAY),null,null,null);
        else
            mAttendingTextView.setCompoundDrawables(new IconicsDrawable(mAttendingTextView.getContext(), FontAwesome.Icon.faw_users).sizeDp(16).color(Color.GRAY),null,null,null);
        if(event.downloadURL!=null)
            Glide.with(mShotImageView.getContext())
                    .load(event.downloadURL)
                    .placeholder(R.drawable.grid_item_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mShotImageView);
        mAttendingTextView.setText(" "+event.registerCount+"");
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
        topCard.setOnClickListener(clickxxx);

    }
    public void removefragment()
    {
        mrootViewCard.setVisibility(View.GONE);
            mcardViewEvent.setVisibility(View.GONE);
        mcardView.setVisibility(View.GONE);

    }
}
