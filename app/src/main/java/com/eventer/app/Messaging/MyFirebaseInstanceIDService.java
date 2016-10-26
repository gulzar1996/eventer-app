package com.eventer.app.Messaging;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by gaurav on 25/10/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {

        String token= FirebaseInstanceId.getInstance().getToken();

        registerToken(token);
    }
    public void registerToken(String token)
    {
        Log.d("token",token);
    }
}
