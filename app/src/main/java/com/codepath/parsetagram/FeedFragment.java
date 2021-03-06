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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {

    ArrayList<Post> posts;
    public RecyclerView rvPost;
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

        loadTopPosts();


        getConfiguration();

        return rootView;

    }


    public void getConfiguration() {

    }

    public void loadTopPosts(){
        final Post.Query postsQuery = new Post.Query();
        postsQuery
                .getTop()
                .withUser();


        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e==null){
                    Post post = new Post();
                    System.out.println("WOOOHOOOOO");
                    for (int i = 0;i<objects.size(); i++){
                        try {
                            Log.d("FeedActivity", "Post ["+i+"] = "
                                    + objects.get(i).getDescription()
                                        + "\n username = " + objects.get(i).getUser().fetchIfNeeded().getUsername()
                                    + " o k ");
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        posts.add(0,objects.get(i));
                        adapter.notifyItemInserted(posts.size()-1);

                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
