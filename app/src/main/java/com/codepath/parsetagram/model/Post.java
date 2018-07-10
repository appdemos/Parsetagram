package com.codepath.parsetagram.model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

//    public ParseUser getPostUser() throws ParseException {
//        ParseQuery query = getRelation(KEY_USER).getQuery();
//        ParseUser postCreatorUser = ParseUser.getCurrentUser();
//        query.whereEqualTo("objectId", postCreatorUser.getObjectId());
//        return (ParseUser) query.find().get(0);
//    }


    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public static class Query extends ParseQuery<Post>{

        public Query() {
            super(Post.class);
        }

        public Query getTop(){
            setLimit(20);
            return this;
        }

        public Query withUser(){
            include("user");
            return this;
        }
    }


}
