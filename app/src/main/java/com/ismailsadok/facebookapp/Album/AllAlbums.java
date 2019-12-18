package com.ismailsadok.facebookapp.Album;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.ismailsadok.facebookapp.Adp.AdpAlbum;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.ismailsadok.facebookapp.MainActivity;
import com.ismailsadok.facebookapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllAlbums extends AppCompatActivity {

    private List<ModuleAlbums> alFBAlbum ;
    RecyclerView recyclerView;
    AdpAlbum adpAlbum;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allalbums);

        alFBAlbum = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.listAlbums);
        adpAlbum = new AdpAlbum(this, alFBAlbum);

        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        getSupportActionBar().setTitle("All Albums");
        topToolBar.setTitleTextColor(Color.WHITE);

        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(getBaseContext(), 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adpAlbum);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Albums");
        dialog.setCancelable(false);


        dialog.show();
        GetUserAlbums();
    }

    private void GetUserAlbums(){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + AccessToken.getCurrentAccessToken().getUserId() + "/"
                        +"albums",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("ModuleAlbums", "Facebook Albums: " + response.toString());
                        try {
                            if (response.getError() == null) {
                                JSONObject joMain = response.getJSONObject();
                                if (joMain.has("data")) {

                                    JSONArray jaData = joMain.optJSONArray("data");
                                    alFBAlbum = new ArrayList<>();

                                    for (int i = 0; i < jaData.length(); i++) {

                                        JSONObject joAlbum = jaData.getJSONObject(i);

                                        ModuleAlbums moduleAlbums = new ModuleAlbums()
                                                .setId(joAlbum.optString("id"))
                                                .setName(joAlbum.optString("name"));
                                        alFBAlbum.add(moduleAlbums);

                                    }

                                    Log.d("ModuleAlbums", "Nombre des albums : " + alFBAlbum.size());

                                    Collections.sort(alFBAlbum);
                                    adpAlbum.setList(alFBAlbum);
                                    adpAlbum.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            } else {
                                Log.d("Test", response.getError().toString());
                                dialog.setMessage("Response Error");
                            }
                        } catch (JSONException e) {
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
            Intent intent = new Intent(AllAlbums.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
