package com.eventer.app.LoginAPI;

import org.json.JSONObject;

/**
 * Created by gaurav on 24/10/16.
 */
public interface ServerCallback {
    void onSuccessResult(JSONObject result);
    void onFailure();
}

