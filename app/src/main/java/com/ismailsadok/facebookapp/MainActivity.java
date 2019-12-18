package com.ismailsadok.facebookapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ismailsadok.facebookapp.Album.AllAlbums;

public class MainActivity extends FragmentActivity {

    LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(AccessToken.getCurrentAccessToken() != null){
            Start();
        }

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile,email,user_photos");




        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FacebookLogin","Login Success " + loginResult.getAccessToken());
                Start();
            }

            @Override
            public void onCancel() {
                Log.d("FacebookLogin","Login Cancel ");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("FacebookLogin","Login Error ", exception);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void Start(){
        Intent intent = new Intent(this, AllAlbums.class);
        this.startActivity(intent);
        finish();
    }
}
