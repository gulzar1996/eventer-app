package com.eventer.app.Event;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventer.app.Chat.ChatActivity;
import com.eventer.app.R;
import com.eventer.app.model.Broadcast;
import com.eventer.app.model.Event;
import com.eventer.app.model.User;
import com.eventer.app.util.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
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
    @BindView(R.id.eventLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.ic_winners)
    TextView mic_winners;
    @BindView(R.id.ic_queries)
    TextView mic_queries;
    @BindView(R.id.ic_organizer)
    TextView mic_organizer;
    @BindView(R.id.icgroup_orsolo)
    TextView micgroup_orsolo;
    @BindView(R.id.eventDescription)
    TextView meventDescription;
    @BindView(R.id.prizeTextView)
    TextView mprizeTextView;
    @BindView(R.id.rulesTextView)
    TextView mrulesTextView;
    @BindView(R.id.eventVenue)
    TextView meventVenue;
    @BindView(R.id.eventDate)
    TextView meventDate;
    @BindView(R.id.app_bar)
    AppBarLayout mapp_bar;
    View positiveAction;
    EditText titleInput;
    EditText messageInput;
    private String uid, eid;
    private User userAdmin;
    private DatabaseReference eRef;

    public final String adminOption[]={"View Registration","Send Notification"};
    long currentDate, eventDate;
    Boolean eventIsToday = false;
    @BindView(R.id.progressLayout)
    View mprogressLayout;
    private DatabaseReference mNotDatabase;

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


        //get event id
        eid = getIntent().getStringExtra("eid");
        //initialize event refrence
        eRef = mDatabase.child("events").child(eid);
        mNotDatabase = FirebaseUtils.getDatabase().getReference();
        //Get Event Object From Previous Class
        mEvent = Parcels.unwrap(getIntent().getParcelableExtra("EXTRA_EVENT"));


        //If its passed via notification
        if(mEvent==null)
        {
            eRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mEvent=dataSnapshot.getValue(Event.class);
                    if(mEvent!=null)
                    {
                        mprogressLayout.setVisibility(View.INVISIBLE);
                        coordinatorLayout.setVisibility(View.VISIBLE);
                        // get Date
                        eventDate = (long) getDateFromString(mEvent.date_time).getTime();
                        currentDate = getCurrentDate().getTime();
                        if (eventDate < currentDate)
                            eventIsToday = true;
                        //load all the details
                        loadDetails(mEvent);
                    }
                    else {
                        Toast.makeText(EventActivity.this, "Strange, It was not expected to happen", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            // get Date
            eventDate = (long) getDateFromString(mEvent.date_time).getTime();
            currentDate = getCurrentDate().getTime();
            if (eventDate < currentDate)
                eventIsToday = true;
            //load all the details
            loadDetails(mEvent);
            mprogressLayout.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }


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
            userAdmin = new User(parts[0], user.getDisplayName());
        } else {
            // do some when no user get found
        }
    }


    private void loadDetails(Event e) {
        mEventName.setText(e.title);
        meventDescription.setText(e.body);
        meventDate.setText(e.date_time);
        mprizeTextView.setText(e.prize);
        mrulesTextView.setText(e.rules);
        micgroup_orsolo.setText(groupOrSolo());
        meventVenue.setText(e.venue);

        //Temp
        Glide.with(this)
                .load(e.downloadURL)
                .placeholder(R.drawable.grid_item_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mShotImageView);
        // fab actions
        if (e.userkey.contains(uid)) {
            userIsAdmin = true;
            fab.setImageDrawable(new IconicsDrawable(getBaseContext(), FontAwesome.Icon.faw_shield).actionBar().color(Color.WHITE));
        } else {
            userIsAdmin = false;
            if (!eventIsToday) {
                if (e.registers.containsKey(uid)) {
                    userIsRegister = true;
                    fab.setImageDrawable(new IconicsDrawable(getBaseContext(), FontAwesome.Icon.faw_times).actionBar().color(Color.WHITE));
                    //add snack bar here
                } else {
                    fab.setImageDrawable(new IconicsDrawable(getBaseContext(), FontAwesome.Icon.faw_check).actionBar().color(Color.WHITE));
                    // add snack bar here
                }
            } else {
                fab.setImageDrawable(new IconicsDrawable(getBaseContext(), FontAwesome.Icon.faw_exclamation).actionBar().color(Color.WHITE));
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Registrations closed", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }
    }

    private String groupOrSolo() {
        int maxReg = mEvent.maxReg;
        int minReg = mEvent.minReg;
        if (minReg == 1 && maxReg == 1) {
            micgroup_orsolo.setCompoundDrawables(null, new IconicsDrawable(this, FontAwesome.Icon.faw_user).actionBar().color(Color.GRAY), null, null);
            return "Solo";
        } else if (minReg == maxReg) {
            micgroup_orsolo.setCompoundDrawables(null, new IconicsDrawable(this, FontAwesome.Icon.faw_users).actionBar().color(Color.GRAY), null, null);
            return "Group of " + maxReg;
        } else {
            micgroup_orsolo.setCompoundDrawables(null, new IconicsDrawable(this, FontAwesome.Icon.faw_users).actionBar().color(Color.GRAY), null, null);
            return "Group " + minReg + "-" + maxReg;
        }
    }

    private void loadIcons() {
        micgroup_orsolo.setCompoundDrawables(null, new IconicsDrawable(this, FontAwesome.Icon.faw_user).actionBar().color(Color.GRAY), null, null);
        mic_winners.setCompoundDrawables(null, new IconicsDrawable(this, FontAwesome.Icon.faw_trophy).actionBar().color(Color.GRAY), null, null);
        mic_organizer.setCompoundDrawables(null, new IconicsDrawable(this, GoogleMaterial.Icon.gmd_verified_user).actionBar().color(Color.GRAY), null, null);
        mic_queries.setCompoundDrawables(null, new IconicsDrawable(this, GoogleMaterial.Icon.gmd_forum).actionBar().color(Color.GRAY), null, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    @OnClick(R.id.floating_action_button)
    void registerEvent() {
        if (!userIsAdmin) {
            if (!eventIsToday) {
                setUpRegistration(eRef, uid, userAdmin);
                changeDesignfab(eRef, uid);
            } else {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Registrations closed", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        } else {

            new MaterialDialog.Builder(this)
                    .title("Admin Options")
                    .icon(new IconicsDrawable(this, FontAwesome.Icon.faw_shield).actionBar().color(Color.BLACK))
                    .items(adminOption)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            if (which==0)
                                getRegisterUser(eRef, true);
                            if (which==1)
                                sendBroadcast();
                               //Open notification broadcast
                        }
                    })
                    .autoDismiss(true)
                    .show();

        }

    }

    @OnClick(R.id.icgroup_orsolo) void groupOrSolomessage()
    {
        if(mEvent.maxReg==1 && mEvent.minReg==1)
            new MaterialDialog.Builder(this)
                    .title("Solo Registration")
                    .content("Individual Participation Only")
                    .positiveText("Capisce")
                    .show();
        else if(mEvent.minReg == mEvent.maxReg)
        {
            new MaterialDialog.Builder(this)
                    .title("Group Registration")
                    .content("A group of only "+mEvent.minReg+" members. If any member of the group fails to register then you wont be allowed to participate")
                    .positiveText("Capisce")
                    .show();
        }
        else
        {
            new MaterialDialog.Builder(this)
                    .title("Group Registration")
                    .content("A group of minimum "+mEvent.minReg+" and maximum "+mEvent.maxReg+" members If any member of the group fails to register then you wont be allowed to participate")
                    .positiveText("Capisce")
                    .show();
        }

    }
    @OnClick(R.id.ic_queries)
    void Query() {
        Intent i = new Intent(this, ChatActivity.class);
        Bundle b = new Bundle();
        b.putParcelable("EXTRA_EVENT", Parcels.wrap(mEvent));
        i.putExtras(b);
        startActivity(i);
    }
    @OnClick(R.id.ic_winners) void winnersAnnouncement()
    {
        if(mEvent.winners==null || mEvent.winners.equalsIgnoreCase(" "))
        new MaterialDialog.Builder(this)
                .title("Winners ")
                .content("Event not finished")
                .positiveText("Ok")
                .show();
        else
            new MaterialDialog.Builder(this)
                    .title("Winners ")
                    .content(mEvent.winners)
                    .positiveText("Hmm")
                    .show();
    }

    @OnClick(R.id.ic_organizer)
    void Organizer() {

        if(mEvent.organizers!=null){
            String namePhone="";
            String[] org = mEvent.organizers.toArray(new String[mEvent.organizers.size()]);
            for(String s:org)
                namePhone+=s+"\n";
            MaterialDialog md= new MaterialDialog.Builder(this)
                    .title("Organizers")
                    .content(namePhone)
                    .show();
            Linkify.addLinks(md.getContentView(), Linkify.PHONE_NUMBERS);
            md.getContentView().setLinksClickable(true);}
    }


    public void sendBroadcast()
    {
        MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .title(R.string.sendNotification)
                        .icon(new IconicsDrawable(this, FontAwesome.Icon.faw_envelope_o).actionBar().color(Color.BLACK))
                        .customView(R.layout.dialog_notification_view, true)
                        .positiveText("Send")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                               mNotDatabase= mNotDatabase.child("broadcast");
                               String key = mNotDatabase.push().getKey();
                               mNotDatabase.child(key).setValue(new Broadcast(mEvent.eventID,FirebaseAuth.getInstance().getCurrentUser().getUid(),messageInput.getText().toString(),titleInput.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful())
                                           Toast.makeText(EventActivity.this, "Notification Sent to Participants", Toast.LENGTH_SHORT).show();
                                       else
                                           Toast.makeText(EventActivity.this, "Notification Failed", Toast.LENGTH_SHORT).show();
                                   }
                               });
                                dialog.dismiss();
                            }
                        }).build();
        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        positiveAction.setEnabled(false);
        titleInput = (EditText) dialog.getCustomView().findViewById(R.id.title);
        messageInput = (EditText) dialog.getCustomView().findViewById(R.id.message);
        titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int userName = editable.length();
                if(userName >=1){
                    tryEnableNotificationSendButton();
                }
            }
        });
        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                positiveAction.setEnabled(charSequence.toString().trim().length() > 7);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int userName = editable.length();
                if(userName >=1){
                    tryEnableNotificationSendButton();
                }
            }
        });
        dialog.show();
    }

    public void tryEnableNotificationSendButton() {
        if (messageInput.getText().toString().length() >= 7 && (titleInput.getText().toString().length() >= 4))
            positiveAction.setEnabled(true);
        else
            positiveAction.setEnabled(false);
    }

    public void makeCall(String text) {
        int s, e;
        s = e = 0;
        String phoneNo;
        for (int i = 0; i < text.length(); i++)
            if (Character.isDigit(text.charAt(i))) {
                s = i;
                break;
            }
        for (int i = text.length() - 1; i >= 0; i--)
            if (Character.isDigit(text.charAt(i))) {
                e = i;
                break;
            }
        phoneNo = text.substring(s, e + 1);
        phoneNo = phoneNo.replaceAll("\\s+", "");
        Toast.makeText(EventActivity.this, "" + phoneNo, Toast.LENGTH_SHORT).show();
        if (phoneNo.length() >= 10) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNo));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                if (ActivityCompat.shouldShowRequestPermissionRationale(EventActivity.this,
                        Manifest.permission.CALL_PHONE)) {
                }
                return;
            }
            startActivity(intent);
        }

    }




}
