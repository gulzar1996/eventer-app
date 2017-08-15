package com.eventer.app.model;

import org.parceler.Parcel;

/**
 * Created by gulza on 8/15/2017.
 */
@Parcel
public class Story {
    public Story(String storyName, String storyUrl,String storyVideoUrl, String storyKey, String storyDescription,String storyAuthor, long timestamp) {
        this.storyName = storyName;
        this.storyUrl = storyUrl;
        this.storyKey = storyKey;
        this.storyDescription = storyDescription;
        this.timestamp = timestamp;
        this.storyVideoUrl=storyVideoUrl;
        this.storyAuthor=storyAuthor;
    }


    public String storyName;
    public String storyUrl;
    public String storyVideoUrl;
    public String storyAuthor;
    public String storyKey;
    public String storyDescription;
    public long timestamp;
    public Story(){}
}
