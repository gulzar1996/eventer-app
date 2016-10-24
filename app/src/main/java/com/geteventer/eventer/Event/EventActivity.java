package com.geteventer.eventer.Event;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.geteventer.eventer.R;
import com.geteventer.eventer.model.Event;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gulzar on 24-10-2016.
 */
public class EventActivity extends AppCompatActivity {

    private Event mEvent;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.imageview_shot)
    ImageView mShotImageView;
    @BindView(R.id.textview_event_name)
    TextView mEventName;
    @BindView(R.id.ic_likes) TextView mic_likes;
    @BindView(R.id.ic_queries)TextView mic_queries;
    @BindView(R.id.ic_reminder)TextView mic_reminder;
    @BindView(R.id.icgroup_orsolo)TextView micgroup_orsolo;
    @BindView(R.id.fab_register)FloatingActionButton mfab_register;
    @BindView(R.id.app_bar)AppBarLayout mapp_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        loadIcons();
        dockFloatingActionButton();
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //Get Event Object From Previous Class
        mEvent = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_EVENT"));
        loadDetails(mEvent);

        //Setting up ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    private void dockFloatingActionButton() {
        mapp_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d("FAB PROB","mapp_bar height :"+mapp_bar.getHeight()+"verticalOffset :"+verticalOffset+"appBarLayout heigh:"+appBarLayout.getHeight());
                if(mapp_bar.getHeight()/2<-verticalOffset)
                    mfab_register.setVisibility(View.GONE);
                else
                    mfab_register.setVisibility(View.VISIBLE);
            }
        });
    }

    private  void  loadDetails(Event e) {
        mEventName.setText(e.title);
        //Temp
        Glide.with(this)
                .load(e.downloadURL)
                .placeholder(R.drawable.grid_item_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mShotImageView);
    }

    private void loadIcons() {

        mfab_register.setImageDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_done).color(Color.WHITE));

        micgroup_orsolo.setCompoundDrawables(null,new IconicsDrawable(this, GoogleMaterial.Icon.gmd_face).actionBar().color(Color.GRAY),null,null);
        mic_likes.setCompoundDrawables(null,new IconicsDrawable(this, GoogleMaterial.Icon.gmd_favorite_border).actionBar().color(Color.GRAY),null,null);
        mic_reminder.setCompoundDrawables(null,new IconicsDrawable(this, GoogleMaterial.Icon.gmd_alarm).actionBar().color(Color.GRAY),null,null);
        mic_queries.setCompoundDrawables(null,new IconicsDrawable(this, GoogleMaterial.Icon.gmd_message).actionBar().color(Color.GRAY),null,null);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }




}
