package com.ismailsadok.facebookapp.Album;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.ismailsadok.facebookapp.Adp.AdpPhotos;


import com.ismailsadok.facebookapp.MainActivity;
import com.ismailsadok.facebookapp.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumImg extends AppCompatActivity    {

    List<ModulePhotos> lstFBImages;
    String AlbumID;

    RecyclerView recyclerView;
    AdpPhotos adpPhotos;
    ProgressDialog dialog;

    MenuItem itemUpload;

    List<String> imageToUploadPath = new ArrayList<>();

    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumimg);

        AlbumID = getIntent().getStringExtra("ALBUM_ID");
        String AlbumName = getIntent().getStringExtra("ALBUM_NAME");

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(AlbumName);
        topToolBar.setTitleTextColor(Color.WHITE);

        lstFBImages = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.listPhotos);
        adpPhotos = new AdpPhotos(this, lstFBImages);

        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getBaseContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adpPhotos);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Chargement des images d'album");
        dialog.setCancelable(false);

        dialog.show();
        GetFacebookImages(AlbumID);

        progressDialog = new ProgressDialog(this);
        alertDialog = new AlertDialog.Builder(this);
    }

    public void GetFacebookImages(final String albumId) {

        Bundle parameters = new Bundle();
        parameters.putString("fields", "images");
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + albumId + "/photos",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {

                        Log.v("AlbumImg", "Facebook photoss response: " + response);
                        try {
                            if (response.getError() == null) {

                                JSONObject joMain = response.getJSONObject();
                                if (joMain.has("data")) {
                                    JSONArray jaData = joMain.optJSONArray("data");

                                    for (int i = 0; i < jaData.length(); i++) {
                                        JSONObject joAlbum = jaData.getJSONObject(i);
                                        JSONArray jaImages = joAlbum.getJSONArray("images");

                                        ModulePhotos photos = new ModulePhotos()
                                                .setId(joAlbum.getString("id"));

                                        if (jaImages.length() > 0) {
                                            ModuleImages images = new ModuleImages()
                                                    .setHeight(jaImages.getJSONObject(0).getInt("height"))
                                                    .setWidth(jaImages.getJSONObject(0).getInt("width"))
                                                    .setSource(jaImages.getJSONObject(0).getString("source"));

                                            photos.setImages(images);

                                            lstFBImages.add(photos);//lstFBImages is Images object array
                                        }
                                    }

                                    adpPhotos.setList(lstFBImages);
                                    adpPhotos.notifyDataSetChanged();
                                    dialog.dismiss();
                                }

                            } else {
                                Log.v("AlbumImg", response.getError().toString());
                                dialog.setMessage("Response Error");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            dialog.setMessage("Exception");
                        }

                    }
                }
        ).executeAsync();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.img_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("dddd","logout");
        if(item.getItemId() == R.id.alog_out){
            LoginManager.getInstance().logOut();
            finish();
            Intent intent = new Intent(AlbumImg.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("Nice", "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }



}
