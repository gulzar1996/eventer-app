package com.eventer.app.model;

import com.google.firebase.database.Exclude;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public String downloadURL;
    public ArrayList<String> tags;
    public String userkey;
    public int registerCount = 0;
    public Map<String, Map<String,Boolean>> registers = new HashMap<>();
    public long timestamp;
    public int maxReg;
    public int minReg;
    public int groupRegisterCount=0;

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Event(String userKey, String title, String body, String date_time,String venue, ArrayList<String> tags,  ArrayList<String> organizers,String downloadURL,long timestamp,int maxReg,int minReg) {

        this.userkey=userKey;

        this.organizers=organizers;

        this.tags=tags;

        this.title = title;
        this.body = body;
        this.date_time=date_time;
        this.venue=venue;
        this.downloadURL=downloadURL;
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
        result.put("organizers",organizers);
        result.put("tags",tags);
        result.put("title", title);
        result.put("body", body);
        result.put("venue",venue);
        result.put("date_time", date_time);
        result.put("registerCount", registerCount);
        result.put("registers", registers);
        result.put("groupRegisterCount", groupRegisterCount);
        result.put("timestamp",timestamp);
        result.put("minReg",minReg);
        result.put("maxReg",maxReg);
        return result;
    }
}
