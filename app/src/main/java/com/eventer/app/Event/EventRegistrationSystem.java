package com.eventer.app.Event;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eventer.app.R;
import com.eventer.app.model.Event;
import com.eventer.app.model.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by gaurav on 25/10/16.
 */
public class EventRegistrationSystem extends AppCompatActivity {
    private Boolean someError=false;
    public Boolean userIsRegister=false,userIsAdmin=false;
    public  DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @BindView(R.id.floating_action_button) FloatingActionButton fab;


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
        mReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Event event = dataSnapshot.getValue(Event.class);
                        if (event.registers.containsKey(uid)) {
                            fab.setImageDrawable(new IconicsDrawable(getBaseContext(), GoogleMaterial.Icon.gmd_clear).actionBar().color(Color.BLACK));
                            // add snack bar here
                        } else {
                            fab.setImageDrawable(new IconicsDrawable(getBaseContext(), GoogleMaterial.Icon.gmd_done).actionBar().color(Color.BLACK));
                            // add snack bar here
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
                            registerUserInDialog(list);
                        }
//                        else
                            //registerUserInExel(list);
                         // [Change Design Accordingly]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("XXXX", "putAttendence:onCancelled", databaseError.toException());
                    }
                });
    }
    private void registerUserInDialog(List<User> users)
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
                .show();
    }


}
