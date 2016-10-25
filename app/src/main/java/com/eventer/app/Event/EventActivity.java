package com.eventer.app.Event;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.Chat.ChatActivity;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.htab_maincontent)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.ic_likes) TextView mic_likes;
    @BindView(R.id.ic_queries)TextView mic_queries;
    @BindView(R.id.ic_reminder)TextView mic_reminder;
    @BindView(R.id.icgroup_orsolo)TextView micgroup_orsolo;
    @BindView(R.id.app_bar)AppBarLayout mapp_bar;
    FloatingActionButton mButton_sent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        loadIcons();
       //
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

     //   showSnackBar();
        //Get Event Object From Previous Class
        mEvent = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_EVENT"));
        loadDetails(mEvent);

        //Setting up ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    private void showSnackBar()
    {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout,"", Snackbar.LENGTH_INDEFINITE)
                .setAction("Register", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
//                        snackbar1.show();
                    }
                });
        snackbar.show();

    }
    @OnClick(R.id.ic_queries) void Query()
    {
        Intent i=new Intent(this,ChatActivity.class);
        Bundle b=new Bundle();
        b.putParcelable("EXTRA_EVENT", Parcels.wrap(mEvent));
        i.putExtras(b);
        startActivity(i);

    }





}
