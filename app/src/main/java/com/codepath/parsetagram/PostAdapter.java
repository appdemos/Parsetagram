package com.codepath.parsetagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.parsetagram.model.Post;
import com.parse.ParseException;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> mPosts;
    Context context;
    //pass in the tweets array
    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_post,parent,false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        try {
            holder.tvUserName.setText(post.getUser().getUsername()); //????
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvDesc.setText(post.getDescription()); //????
//
//        Glide.with(context).load(post.user.profileImageUrl).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImage;
        public TextView tvUserName;
        public TextView tvDesc;



        public ViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUser);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDescription);

        }
    }
}
