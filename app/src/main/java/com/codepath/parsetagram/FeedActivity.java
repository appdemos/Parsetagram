package com.codepath.parsetagram;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.codepath.parsetagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class FeedActivity extends AppCompatActivity {

    ImageButton ibHome;
    ImageButton ibProfile;
    ImageButton ibAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ibAdd = (ImageButton) findViewById(R.id.ibAdd);
        ibHome = (ImageButton) findViewById(R.id.ibHome);
        ibProfile = (ImageButton) findViewById(R.id.ibProfile);

        final Post.Query postsQuery = new Post.Query();
        postsQuery
                .getTop()
                .withUser();


        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e==null){
                    System.out.println("WOOOHOOOOO");
                    for (int i = 0;i<objects.size(); i++){
                        try {
                            Log.d("FeedActivity", "Post ["+i+"] = "
                                    + objects.get(i).getDescription()
                                    + "\n username = " + objects.get(i).getUser().getUsername());
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

}

    public void changeFragment(View view){
        Fragment fragment;

        if (view == findViewById(R.id.ibHome)) {

            ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
            ibHome.setImageResource(R.drawable.instagram_home_filled_24);
            ibProfile.setImageResource(R.drawable.instagram_user_outline_24);
            FeedFragment feedFragment = new FeedFragment();
            fragment = feedFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();


        } else if (view == findViewById(R.id.ibAdd)) {

            ibAdd.setImageResource(R.drawable.instagram_new_post_filled_24);
            ibHome.setImageResource(R.drawable.instagram_home_outline_24);
            ibProfile.setImageResource(R.drawable.instagram_user_outline_24);

            AddPhotoFragment addPhotoFragment = new AddPhotoFragment();
            fragment = addPhotoFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();

        } else if (view == findViewById(R.id.ibProfile)) {


            ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
            ibHome.setImageResource(R.drawable.instagram_home_outline_24);
            ibProfile.setImageResource(R.drawable.instagram_user_filled_24);

            ProfileFragment profileFragment = new ProfileFragment();
            fragment = profileFragment;

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragmentPlace, fragment);

            ft.commit();
        }

    }



}
