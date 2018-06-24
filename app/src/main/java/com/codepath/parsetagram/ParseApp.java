package com.codepath.parsetagram;

import android.app.Application;

import com.codepath.parsetagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("parsetagram")
                .clientKey("adfasdassomethingsuperrandomaddas")
                .server("http://parsetagramm.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
