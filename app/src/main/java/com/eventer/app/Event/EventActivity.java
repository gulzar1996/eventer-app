package com.eventer.app.Event;

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

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.Chat.ChatActivity;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.eventer.app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gulzar on 24-10-2016.
 */
public class EventActivity extends EventRegistrationSystem {

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
    @BindView(R.id.ic_organizer)TextView mic_organizer;
    @BindView(R.id.icgroup_orsolo)TextView micgroup_orsolo;
    @BindView(R.id.eventDescription) TextView meventDescription;
    @BindView(R.id.app_bar)AppBarLayout mapp_bar;
    private String uid,eid;
    private User userAdmin;
    private DatabaseReference eRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);
        loadIcons();
       //
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        //get user details
        getUserDetails();

        showSnackBar();

        //get event id
        eid=getIntent().getStringExtra("eid");
        //initialize event refrence
        eRef=mDatabase.child("events").child(eid);

        //Get Event Object From Previous Class
        mEvent = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_EVENT"));
        loadDetails(mEvent);
        getRegisterUser(eRef,true);
        //Setting up ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    // get user detials
    void getUserDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            uid = user.getUid();
            String[] parts = user.getEmail().split("@");
            userAdmin=new User(parts[0],user.getDisplayName());
        }
        else
        {
            // do some when no user get found
        }
    }


    private  void  loadDetails(Event e) {
        mEventName.setText(e.title);
        meventDescription.setText(e.body);

        micgroup_orsolo.setText(groupOrSolo());

        //Temp
        Glide.with(this)
                .load(e.downloadURL)
                .placeholder(R.drawable.grid_item_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mShotImageView);
    }

    private String groupOrSolo() {
        int maxReg=mEvent.maxReg;
        int minReg=mEvent.minReg;
        if(minReg==1 && maxReg==1)
            return "Solo";
        else
            if(minReg==maxReg)
             return "Group of "+maxReg;
        else
             return "Group "+minReg+"-"+maxReg;
    }

    private void loadIcons() {
        micgroup_orsolo.setCompoundDrawables(null,new IconicsDrawable(this, GoogleMaterial.Icon.gmd_face).actionBar().color(Color.GRAY),null,null);
        mic_likes.setCompoundDrawables(null,new IconicsDrawable(this, GoogleMaterial.Icon.gmd_favorite_border).actionBar().color(Color.GRAY),null,null);
        mic_organizer.setCompoundDrawables(null,new IconicsDrawable(this, GoogleMaterial.Icon.gmd_verified_user).actionBar().color(Color.GRAY),null,null);
        mic_queries.setCompoundDrawables(null,new IconicsDrawable(this, GoogleMaterial.Icon.gmd_forum).actionBar().color(Color.GRAY),null,null);
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
                        setUpRegistration(eRef,uid,userAdmin);

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
        overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.pull_up_from_bottom);
    }
    @OnClick(R.id.ic_organizer)void Organizer()
    {
        //Converting ArrayList to array
        String[] org = mEvent.organizers.toArray(new String[mEvent.organizers.size()]);
        new MaterialDialog.Builder(this)
                .title("Organizers")
                .items(org)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    }})
                .show();
    }
    @OnClick(R.id.icgroup_orsolo) void groupOrSoloNamesDisplay()
    {
        getRegisterUser(eRef,true);
    }





}
