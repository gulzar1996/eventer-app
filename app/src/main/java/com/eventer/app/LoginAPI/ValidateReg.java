package com.eventer.app.LoginAPI;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.eventer.app.BuildConfig;
import com.eventer.app.EventerApp;
import com.eventer.app.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gulzar on 24/10/16.
 */
public class ValidateReg{


    public ValidateReg()  {
    }
    public void validateRegJSON(final String username, final String password, final Context context, final ServerCallback callback)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        //Something is missing here
        String epassword= password;
        final String url = context.getResources().getString(R.string.apikey)+""+username+"&pass="+epassword;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccessResult(response);
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure();
                    }
                }
        );
        queue.add(getRequest);



    }



}
