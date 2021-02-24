package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;

    //Reference to the Tweet class
    Tweet tweet = new Tweet();

    // Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(tweet.isRetweet){
            View retweetView = LayoutInflater.from(context).inflate(R.layout.item_retweet, parent, false);
            return new ViewHolder(retweetView);
        }
        else {
            View normalView = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
            return new ViewHolder(normalView);
        }
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);
        // Bind the tweet with view holder
        if(tweet.isRetweet){
            holder.bindRetweet(tweet);
        }
        else {
            holder.bind(tweet);
        }
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    // Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvTimestamp;
        TextView tvRetweet;
        ImageView ivRetweet;
        ImageView ivProfileImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivRetweet = itemView.findViewById(R.id.ivRetweet);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvRetweet = itemView.findViewById(R.id.tvRetweet);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvName.setText(tweet.user.name);
            tvScreenName.setText(tweet.user.screenName);
            tvTimestamp.setText(tweet.getFormattedTimestamp());

            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
        }

        public void bindRetweet(Tweet tweet) {

            tvBody.setText(fixBodyText(tweet.body));
            if(tweet.retweeter != null) {
                tvName.setText(tweet.retweeter.name);
                tvScreenName.setText(tweet.retweeter.screenName);
                tvTimestamp.setText(tweet.getFormattedTimestamp());

                Glide.with(context).load(tweet.retweeter.profileImageUrl).into(ivProfileImage);
            }
        }

        public String fixBodyText(String body){
            String fixedBody;
            int colonIndex = body.indexOf(":");
            fixedBody = body.substring(colonIndex + 2);
            return fixedBody;
        }
    }
}