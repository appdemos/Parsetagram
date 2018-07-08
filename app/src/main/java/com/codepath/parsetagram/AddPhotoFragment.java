package com.codepath.parsetagram;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.codepath.parsetagram.model.Post;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddPhotoFragment extends Fragment {

    public final static int PICK_PHOTO_CODE = 1046;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;

    Button button;
    ImageView imageView;
    EditText description;
    Button postBtn;
    String currentPhotoPath = "/storage/emulated/0/DCIM/Camera/IMG_20180614_110011.jpg";
    ParseFile parseFile;

    ///
    public final String APP_TAG = "MyCustomApp";
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_add_photo,
                container, false);

        button = (Button) rootView.findViewById(R.id.btnClick);
        imageView = (ImageView) rootView.findViewById(R.id.ivPreview);
        description = (EditText) rootView.findViewById(R.id.etDescription);
        postBtn = (Button) rootView.findViewById(R.id.btnPost);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //
                File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

                    // Create the storage directory if it does not exist
                    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                        Log.d(APP_TAG, "failed to create directory");
                    }

                    // Return the file target for the photo based on filename
//                    File file = new File(mediaStorageDir.getPath() + File.separator + photoFileName);

                    File file = new File(currentPhotoPath);

                    photoFile = file;
                //
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String desc = description.getText().toString();
                final ParseUser currUser = ParseUser.getCurrentUser();
                final File file = photoFile;

                parseFile = new ParseFile(file);
                System.out.println("OKAY WANNA POST HUH?");

                postPhoto(desc, parseFile, currUser);
            }
        });

        return rootView;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                imageView.setImageBitmap(bitmap);

            }
        }
    }


    public void postPhoto(final String description, final ParseFile imageFile, final ParseUser user){


        imageFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    final Post newPost = new Post();
                    newPost.setDescription(description);
                    newPost.setImage(imageFile);
                    newPost.setUser(user);


                    Toast.makeText(getContext(),"Post created woooooohoooo",Toast.LENGTH_LONG).show();
                    Log.d("AddPhotoFragment", description);
                } else {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Post not posted :(",Toast.LENGTH_LONG).show();
                    String s = "NOOOOT HAPPENING :/ " + e.getMessage() + " ... ";
                    Log.d("AddPhotoFragment",s);

                }
            }
        });
    }
}
