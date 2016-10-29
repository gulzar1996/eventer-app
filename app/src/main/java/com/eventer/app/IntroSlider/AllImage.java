package com.eventer.app.IntroSlider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eventer.app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gulzar on 30-10-2016.
 */
public abstract class AllImage extends Fragment{
    public DatabaseReference mDatabase;
    public DatabaseReference mImage1,mImage2;
    public ImageView imageViewFragment;
    public View rootView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView  = inflater.inflate(R.layout.fragment_sliderimage,container,false);
        imageViewFragment= (ImageView) rootView.findViewById(R.id.imageSlider);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return rootView;
    }
}
