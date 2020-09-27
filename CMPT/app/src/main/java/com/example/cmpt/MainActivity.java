package com.example.cmpt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;


import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ImageView iv;
    ProgressBar progressBar;
    String s, n, r;
    boolean done;
    private GestureDetector gestureDetector;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        iv = (ImageView) findViewById(R.id.memeimageView);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        @SuppressLint("WrongConstant") SharedPreferences sh
                = getSharedPreferences("MySharedPref",
                MODE_APPEND);
            boolean q=sh.getBoolean("done",false);
            done=q;
        setrules();
        deleteCache();
        loadmeme();


        gestureDetector = new GestureDetector(this);
    }

    public void setrules() {


        if (done == false) {
            View checkBoxView = View.inflate(this, R.layout.checkbox, null);
            CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    done = true;
                    SharedPreferences sh
                            = getSharedPreferences("MySharedPref",
                            MODE_PRIVATE);
                    SharedPreferences.Editor myEdit
                            = sh.edit();
                    myEdit.putBoolean("done", done);
                    myEdit.commit();
                }
            });
            checkBox.setText("Don't Show it Again");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("How To...")
                    .setMessage("\n\nSwipe Up *SHARE IMAGE*\n" +
                            "Swipe Down *DOWNLOAD IMAGE*\n" +
                            "Swipe Left *MEMEs*\n" +
                            "Swipe Down *NEXT IMAGE*\n"+
                            "Top Right button *SUBREDDDIT SEARCH*\n" )
                    .setView(checkBoxView)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    }).show();

        }
    }

    public void deleteCache() {
        FileUtils.deleteQuietly(this.getCacheDir());
    }


    public void loadmeme() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme/dankmemes";
        progressBar.setVisibility(View.VISIBLE);

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String url = response.getString("url");
                            s = url;
                            Glide.with(MainActivity.this).load(url).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(iv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Cant Load Image.... Try Again", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        r = "meme";
    }

    public void loadcust() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme/" + n;
        progressBar.setVisibility(View.VISIBLE);

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String url = response.getString("url");
                            s = url;
                            Glide.with(MainActivity.this).load(url).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(iv);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Subreddit Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        r = "cust";
    }


    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            String root = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).toString();
            File myDir = new File(root + "/.Sabreddits");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            File file = new File(String.valueOf(myDir), "sabreddit_" + System.nanoTime() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }


    public void sharememe(View view) {
        /*Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/*");
        share.putExtra(Intent.EXTRA_TEXT, s);
        startActivity(Intent.createChooser(share, "Share Image"));*/
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();

        Uri bmpUri = getLocalBitmapUri(iv);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            System.out.println(" ...sharing failed, handle error");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_custom:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Subreddit Name");


                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        n = input.getText().toString();
                        loadcust();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        final int SWIPE_THRESHOLD = 100;
        final int SWIPE_VELOCITY_THRESHOLD = 100;
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();
        // which was greater?  movement across Y or X?
        if (Math.abs(diffX) > Math.abs(diffY)) {
            // right or left swipe
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                result = true;
            }
        } else {
            // up or down swipe
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        }

        return result;
    }

    private void onSwipeTop() {
        Toast.makeText(this, "Share image...", Toast.LENGTH_SHORT).show();
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();

        Uri bmpUri = getLocalBitmapUri(iv);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            System.out.println(" ...sharing failed, handle error");
        }
    }

    private void onSwipeBottom() {
        Toast.makeText(this, "Swipe Bottom", Toast.LENGTH_LONG).show();
        isWriteStoragePermissionGranted();

        Uri bmpUri = getLocalBitmapUri(iv);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Toast.makeText(this, "Image Saved in Downloads Folder", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Image Download Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void onSwipeLeft() {
        Toast.makeText(this, "Back To Memes", Toast.LENGTH_SHORT).show();
        loadmeme();
    }

    private void onSwipeRight() {
        if (r == "cust") {
            loadcust();
        } else {
            loadmeme();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}

