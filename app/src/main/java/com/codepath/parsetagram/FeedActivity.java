package com.codepath.parsetagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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


    }

    public void ibAddOnClick(View view) {
        ibAdd.setImageResource(R.drawable.instagram_new_post_filled_24);
        ibHome.setImageResource(R.drawable.instagram_home_outline_24);
        ibProfile.setImageResource(R.drawable.instagram_user_outline_24);
        Intent addPhotoIntent = new Intent(FeedActivity.this, AddPhotoActivity.class);
        startActivity(addPhotoIntent);

    }

    public void ibHomeOnClick(View view) {
        ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
        ibHome.setImageResource(R.drawable.instagram_home_filled_24);
        ibProfile.setImageResource(R.drawable.instagram_user_outline_24);

    }

    public void ibProfileOnClick(View view) {

        ibAdd.setImageResource(R.drawable.instagram_new_post_outline_24);
        ibHome.setImageResource(R.drawable.instagram_home_outline_24);
        ibProfile.setImageResource(R.drawable.instagram_user_filled_24);
        Intent profileIntent = new Intent(FeedActivity.this, ProfileActivity.class);
        startActivity(profileIntent);
    }
}
