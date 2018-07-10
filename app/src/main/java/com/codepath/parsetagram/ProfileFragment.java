package com.codepath.parsetagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

    Button logoutBtn;
    TextView tvUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutBtn = rootView.findViewById(R.id.btnLogout);
        tvUser = rootView.findViewById(R.id.mYuserName);
        String currentUser = ParseUser.getCurrentUser().getUsername(); // this will now be null
        System.out.println("The current user is "+ currentUser);
        tvUser.setText("Hey "+ currentUser + ", \n do you wish to logout?");

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser(v);
            }
        });
        return rootView;
    }

    public void logoutUser(View view) {

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        System.out.println("The current user is "+ currentUser);
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }

}