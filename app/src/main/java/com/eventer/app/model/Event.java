package com.eventer.app.model;

import com.google.firebase.database.Exclude;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Gulzar on 24-10-2016.
 */
@Parcel
public class Event {

    public ArrayList<String> organizers;
    public String title;
    public String body;
    public String date_time;
    public String venue;
    public String rules;
    public String downloadURL;
    public String logoURL;
    public String prize;
    public ArrayList<String> winners;
    public ArrayList<String> userkey;
    public int registerCount = 0;
    public Map<String,User> registers = new HashMap<>();
    public long timestamp;
    public int maxReg;
    public int minReg;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Event(ArrayList<String> userKey, String title, String body, String date_time,String venue,String rules,  ArrayList<String> organizers,String downloadURL,String logoURL,String prize,long timestamp,int maxReg,int minReg) {

        this.userkey=userKey;

        this.organizers=organizers;


        this.title = title;
        this.body = body;
        this.date_time=date_time;
        this.venue=venue;
        this.rules=rules;
        this.downloadURL=downloadURL;
        this.logoURL=logoURL;
        this.prize=prize;
        this.timestamp=timestamp;
        this.maxReg=maxReg;
        this.minReg=minReg;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userkey", userkey);
        result.put("downloadURL",downloadURL);
        result.put("logoURL",logoURL);
        result.put("organizers",organizers);
        result.put("prize",prize);
        result.put("winners",winners);
        result.put("title", title);
        result.put("body", body);
        result.put("venue",venue);
        result.put("date_time", date_time);
        result.put("rules",rules);
        result.put("registerCount", registerCount);
        result.put("registers", registers);
        result.put("timestamp",timestamp);
        result.put("minReg",minReg);
        result.put("maxReg",maxReg);
        return result;
    }
}
