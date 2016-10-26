package com.eventer.app.Event;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.eventer.app.UiCallBack.GroupInputDetails;
import com.eventer.app.model.Event;
import com.eventer.app.model.User;
import com.eventer.app.ui.GroupDialogBox;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gaurav on 25/10/16.
 */
public class EventRegistrationSystem extends AppCompatActivity {
    private Boolean someError=false;
    public Boolean userIsRegister=false;
    List<User> list=new ArrayList<User>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void setUpRegistration(String eid, final Event mEvent, String uid, final User userAdmin) {

            if(eventIsToday==false && someError==false) {
                onRegisterClicked(mEventReference, uid, userAdmin);
                onRegisterClicked(mUserEventReference, uid, userAdmin);
                changeDesignForUser();
            }


    }
    public void onRegisterClicked(DatabaseReference mReference,final String uid ,final User userAdmin) {
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
                    p.registers.put(uid,userAdmin.toMap());
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
    void changeDesignForUser()
    {
        //flip the design of floating button
    }
}
