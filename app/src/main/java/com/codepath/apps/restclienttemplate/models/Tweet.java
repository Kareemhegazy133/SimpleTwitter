package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    @PrimaryKey
    public long id;

    @Ignore
    public User user;

    @ColumnInfo
    public long userId;

    @ColumnInfo
    public boolean isRetweet;

    @Ignore
    public User retweeter;

    //Empty Constructer needed by the Parceler Library
    public Tweet(){ }
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {

        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.isRetweet = tweet.body.startsWith("RT");
        tweet.userId = user.id;
        return tweet;
    }

    public void setRetweeter(User retweeter){
        this.retweeter = retweeter;
    }
    public String getFormattedTimestamp(){
        return TimeFormatter.getTimeDifference(createdAt);
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

}
