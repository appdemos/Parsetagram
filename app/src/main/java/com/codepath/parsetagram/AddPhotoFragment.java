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
import com.loopj.android.http.AsyncHttpClient;
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
import java.util.ArrayList;
import java.util.Date;


public class AddPhotoFragment extends Fragment {

    public final static int PICK_PHOTO_CODE = 1046;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    Button button;
    ImageView imageView;
    EditText etDescription;
    Button postBtn;
    String currentPhotoPath = "/storage/emulated/0/DCIM/Camera/IMG_20180614_110011.jpg";
    String currentPath;
    ParseFile parseFile;
    ArrayList<Post> posts;
    PostAdapter adapter;
    AsyncHttpClient client;


    ///
    public final String APP_TAG = "MyCustomApp";
    File photoFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_add_photo,
                container, false);

        button = (Button) rootView.findViewById(R.id.btnClick);
        imageView = (ImageView) rootView.findViewById(R.id.ivPreview);
        etDescription = (EditText) rootView.findViewById(R.id.etDescription);
        postBtn = (Button) rootView.findViewById(R.id.btnPost);

        client = new AsyncHttpClient();
        posts = new ArrayList<>();

        adapter = new PostAdapter(posts);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activity activity = AddPhotoFragment.this.getActivity();

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File mediaStorage = null;
                try {
                    mediaStorage = getTempImageFile(getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Create the storage directory if it does not exist
                if (!mediaStorage.exists() && !mediaStorage.mkdirs()){
                    Log.d(APP_TAG, "failed to create directory");
                }

                String path = mediaStorage.getAbsolutePath();
                Uri uri = FileProvider.getUriForFile(activity, "com.codepath.parsetagram", mediaStorage);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                photoFile = new File(path);
                //
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String desc = etDescription.getText().toString();
                final ParseUser currUser = ParseUser.getCurrentUser();
                final File file = photoFile;

                parseFile = new ParseFile(file);
                System.out.println("OKAY WANNA POST HUH?");

                postPhoto(desc, parseFile, currUser);
            }
        });

        return rootView;

    }
    private static File getTempImageFile(Context context) throws IOException {
        String currTime = getCurrentTimestamp();
        String fileNamePrefix = "JPEG_" + currTime + "_";
        String fileNameSuffix = ".jpg";
        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(fileNamePrefix, fileNameSuffix, directory);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                currentPath = photoFile.getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(currentPath);
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
                    newPost.saveInBackground();
                    posts.add(newPost);

                    adapter.notifyItemInserted(posts.size()-1);

                    System.out.println("woooohooooo posted?");
                    Toast.makeText(getContext(),"Post created woooooohoooo",Toast.LENGTH_LONG).show();
                    Log.d("AddPhotoFragment", description);
                    etDescription.setText("");
                    imageView.setImageResource(R.drawable.tick_40143_1280);

                } else {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Post not posted :(",Toast.LENGTH_LONG).show();
                    String s = "NOOOOT HAPPENING :/ " + e.getMessage() + " ... ";
                    Log.d("AddPhotoFragment",s);

                }
            }
        });
    }


    public static String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
}
