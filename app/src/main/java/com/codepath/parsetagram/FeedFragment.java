package com.codepath.parsetagram;

import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.parsetagram.model.Post;

import java.util.ArrayList;


public class FeedFragment extends Fragment {

    ArrayList<Post> posts;
    RecyclerView rvPost;
    PostAdapter adapter;
    AsyncHttpClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        client = new AsyncHttpClient();
        posts = new ArrayList<>();

        adapter = new PostAdapter(posts);
        rvPost = (RecyclerView) rootView.findViewById(R.id.rvPosts);

        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPost.setAdapter(adapter);

        getConfiguration();

        return rootView;

    }


    public void getConfiguration() {

    }
}
