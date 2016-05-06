package com.wenzin.helena.spotifysearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;

public class MainActivity extends Activity implements ConnectionStateCallback {

    public static final String CLIENT_ID = "1eaa887d83324f4baa66d9608f9d8d5b";
    private static final String REDIRECT_URI = "spotifysearch-login://callback";

    public final static String EXTRA_MESSAGE = "com.wenzin.helena.spotifysearch.MESSAGE";
    public final static String ACCESS_TOKEN_MESSAGE = "com.wenzin.helena.spotifysearch.ACCESS_TOKEN_MESSAGE";

    private EditText searchWord;
    private String accessToken;

    private SpotifyApiController spotifyApiController;

    // Request code that will be used to verify if the result comes from correct activity
    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doAuthentication();
    }

    private void doAuthentication() {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        checkRequestCodeAndSaveSearchwordAndAccessToken(requestCode, resultCode, intent);
    }

    private void checkRequestCodeAndSaveSearchwordAndAccessToken(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE) {
            searchWord = (EditText) findViewById(R.id.textSearch);
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                accessToken = response.getAccessToken();
            }
        }
    }

    public void goFindTracks(View view) {
        startActivity(getDisplayTracksIntent());
    }

    @NonNull
    private Intent getDisplayTracksIntent() {
        Intent intent = new Intent(this, DisplayTracksActivity.class);
        intent.putExtra(EXTRA_MESSAGE, searchWord.getText().toString());
        intent.putExtra(ACCESS_TOKEN_MESSAGE, accessToken);
        return intent;
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }
}