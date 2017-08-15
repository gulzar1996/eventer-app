package com.eventer.app.IntroSlider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.AboutPage.AboutActivity;
import com.eventer.app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gulzar on 30-10-2016.
 */
public class Image2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.eventer_logo, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }
    @OnClick(R.id.awesome_guys)void awesomeGuys()
    {
        startActivity(new Intent(getContext(), AboutActivity.class));
    }
}
