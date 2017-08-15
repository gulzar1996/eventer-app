package com.eventer.app.Story;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.eventer.app.R;
import com.eventer.app.model.Event;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gulzar on 8/15/2017.
 */

public class StoryActivity extends AppCompatActivity implements OnPreparedListener {
    @BindView(R.id.video_view)
    VideoView mvideoView;
    private Event mEvent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        //Get Event Object From Previous Class
        mEvent = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_EVENT"));


        Glide.with(this)
                .load(mEvent.downloadURL)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mvideoView.getPreviewImageView());


        setupVideoView();

    }

    public void setupVideoView() {
        // Make sure to use the correct VideoView import
        mvideoView.setOnPreparedListener(this);

        //For now we just picked an arbitrary item to play
        mvideoView.setVideoURI(Uri.parse("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"));
    }
    @Override
    public void onPrepared() {
        Toast.makeText(this, "Starting", Toast.LENGTH_SHORT).show();
        mvideoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
