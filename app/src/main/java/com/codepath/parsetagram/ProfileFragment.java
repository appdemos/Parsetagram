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

import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void logoutUser(View view) {

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }

}