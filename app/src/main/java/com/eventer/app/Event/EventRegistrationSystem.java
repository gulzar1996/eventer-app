package com.eventer.app.Event;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.eventer.app.model.User;

import com.eventer.app.util.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by gaurav on 25/10/16.
 */
public class EventRegistrationSystem extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private Boolean mInitFragment=false;
    private DatabaseReference arefx=null;


    private Boolean someError=false;
    public Boolean userIsRegister=false,userIsAdmin=false;
    public  DatabaseReference mDatabase = FirebaseUtils.getDatabase().getReference();;
    @BindView(R.id.floating_action_button) FloatingActionButton fab;
    @BindView(R.id.eventLayout) CoordinatorLayout coordinatorLayout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUpRegistration(DatabaseReference mReference, String uid, final User userAdmin) {


        if (someError == false) {
            onRegisterClicked(mReference, uid, userAdmin);
        }
    }


    public void onRegisterClicked(final DatabaseReference mReference, final String uid , final User userAdmin) {
        mReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Event p = mutableData.getValue(Event.class);
                if(p == null) {
                    return Transaction.success(mutableData);
                }
                if (p.registers.containsKey(uid)) {
                    // Unregister the event  and remove self from register
                    p.registerCount = p.registerCount - 1;
                    p.registers.remove(uid);
                    userIsRegister=false;
                }
                else {
                    // register the event and add self to register
                    p.registerCount = p.registerCount + 1;
                    p.registers.put(uid,userAdmin);
                    userIsRegister=true;
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("registerClicked", "eventTransaction:onComplete:" + databaseError);
            }
        });
    }
    void changeDesignfab(DatabaseReference mReference,final String uid)
    {
        mReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Event event = dataSnapshot.getValue(Event.class);
                        if (event.registers.containsKey(uid)) {
                            fab.setImageDrawable(new IconicsDrawable(getBaseContext(), FontAwesome.Icon.faw_times).actionBar().color(Color.WHITE));
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "Registered Successfully", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            //Add User to Topic
                            FirebaseMessaging.getInstance().subscribeToTopic(dataSnapshot.getKey());
                            // add snack bar here
                        } else {
                            fab.setImageDrawable(new IconicsDrawable(getBaseContext(), FontAwesome.Icon.faw_check).actionBar().color(Color.WHITE));
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "Unregistered", Snackbar.LENGTH_LONG);
                            snackbar.setActionTextColor(Color.RED);
                            snackbar.show();
                            // add snack bar here
                            //Remove user from Topic
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(dataSnapshot.getKey());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("XXXX", "DesignChangeError:onCancelled", databaseError.toException());
                    }
                });

    }
    public void getRegisterUser(final DatabaseReference aref,final Boolean design ) {
        aref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Event event = dataSnapshot.getValue(Event.class);
                        List<User> list=new ArrayList<User>();
                        for(String uid:event.registers.keySet()) {
                            list.add(event.registers.get(uid));
                        }
                        if(design==true){
                            registerUserInDialog(list,aref);
                        }
                        else
                            registerUserInExel(list,event.title);
                         // [Change Design Accordingly]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("XXXX", "putAttendence:onCancelled", databaseError.toException());
                    }
                });
    }
    private void registerUserInDialog(List<User> users, final DatabaseReference aref)
    {

//        // put material dialog and all the user is in users array list with datastructure User
        String[] username=new String[users.size()];
        for(int i=0;i<users.size();i++)
            username[i]=users.get(i).name+" ("+users.get(i).regno+")";

        new MaterialDialog.Builder(this)
                .title("Participant")
                .items(username)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    }})
                .positiveText("Download")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //todo add permission

                        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //if permission is not granted, show permission rationale and grant permission
                            ActivityCompat.requestPermissions(EventRegistrationSystem.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                        }
                        else {
                            arefx=aref;
                            getRegisterUser(aref,false);
                        }

                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mInitFragment && arefx!=null) {
            getRegisterUser(arefx,false);
            mInitFragment = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
                    mInitFragment=true;
                } else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    // exel export
    void registerUserInExel(List<User> users,String EventTitle)
    {
        // check if available and not read only;
        String fileName = EventTitle.toString() + ".csv";//like 2016_01_12.txt
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "EVENTER");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            final File gpxfile = new File(root, fileName);
            if (gpxfile.exists())
                gpxfile.delete();
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append( EventTitle.toString() + ":," + "\n\n");

            writer.append("SL No."+","+"REG NO" + "," + "NAME" + "\n");
            int userCount=1;
            for (User u : users) {
                writer.append(String.valueOf(userCount)+","+u.regno + "," + u.name + "\n");
                userCount++;
            }
            writer.flush();
            writer.close();
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Download Excel File Successful", Snackbar.LENGTH_LONG)
                    .setAction("VISIT", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(gpxfile),getMimeType(gpxfile.getAbsolutePath()));
                        startActivity(intent);
                    }
                    });
            snackbar.show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Download Unsuccessful\nCheck Permission", Snackbar.LENGTH_LONG);
            snackbar.show();

        }


    }
    //[Get Which File Type To Open With]
    private String getMimeType(String url)
    {
        String parts[]=url.split("\\.");
        String extension=parts[parts.length-1];
        String type = null;
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }
    //[End Exel]
    //[GET DATE]
    public Date getDateFromString(String dateString)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    //[get current date]
    public Date getCurrentDate()
    {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        return cal.getTime();

    }
    //[End Of Date Section]



}
