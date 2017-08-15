package com.eventer.app.Story;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devbrackets.android.exomedia.listener.OnCompletionListener;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.eventer.app.R;
import com.eventer.app.model.Story;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gulzar on 8/15/2017.
 */

public class StoryActivity extends AppCompatActivity implements OnPreparedListener, OnCompletionListener, OnErrorListener {
    @BindView(R.id.video_view)
    VideoView mvideoView;
    @BindView(R.id.pg_bar)
    ProgressBar mpg_bar;
    private Story mStory;
    @BindView(R.id.video_preview)
    ImageView mvideo_preview;
    DatabaseReference mdatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        //Get Event Object From Previous Class
        mStory = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_STORY"));

        mdatabase= FirebaseDatabase.getInstance().getReference();

        Glide.with(this)
                .load(mStory.storyUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(mvideo_preview);
        setupVideoView();

    }

    public void setupVideoView() {
        // Make sure to use the correct VideoView import
        mvideoView.setOnPreparedListener(this);
        mvideoView.setOnCompletionListener(this);
        mvideoView.setOnErrorListener(this);

        //For now we just picked an arbitrary item to play
        mvideoView.setVideoURI(Uri.parse(mStory.storyVideoUrl));
    }
    @Override
    public void onPrepared() {
        mvideoView.setVisibility(View.VISIBLE);
        mvideo_preview.setVisibility(View.GONE);
        mpg_bar.setVisibility(View.GONE);
        mvideoView.start();
    }

    @Override
    protected void onDestroy() {
        if(mvideoView.isPlaying())
        mvideoView.stopPlayback();
        mvideoView.setVisibility(View.GONE);
        mvideo_preview.setVisibility(View.VISIBLE);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mvideoView.isPlaying())
            mvideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mvideoView.isPlaying())
            mvideoView.start();
    }

    @Override
    public void onCompletion() {
        mdatabase.child("watched-stories").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(mStory.storyKey).setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
            }
        });

    }

    @Override
    public boolean onError(Exception e) {
        Toast.makeText(this, "Funny Error", Toast.LENGTH_SHORT).show();
        finish();
        return false;
    }
}
