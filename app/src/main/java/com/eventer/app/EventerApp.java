package com.eventer.app;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Gulzar on 24-10-2016.
 */
public class EventerApp extends Application {

    public static final String TAG = EventerApp.class
            .getSimpleName();
    private static Context sContext;
    private RequestQueue mRequestQueue;
    private static EventerApp mInstance;

    @Override public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
        mInstance = this;
    }
    public static synchronized EventerApp getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static Context context() {
        return sContext;
    }

    public static int color(@ColorRes int resId) {
        return ContextCompat.getColor(sContext, resId);
    }

    public static Drawable drawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(sContext, resId);
    }

    public static int integer(@IntegerRes int resId) {
        return sContext.getResources().getInteger(resId);
    }

    public static String string(@StringRes int resId) {
        return sContext.getString(resId);
    }

    public static String plural(@PluralsRes int resId, int quantity) {
        return sContext.getResources().getQuantityString(resId, quantity, quantity);
    }
}
