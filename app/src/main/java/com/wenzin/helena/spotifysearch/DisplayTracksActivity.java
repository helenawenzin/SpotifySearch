package com.wenzin.helena.spotifysearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

public class DisplayTracksActivity extends AppCompatActivity {

    private SpotifyApiController spotifyApiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tracks);

        System.out.println("I have entered DisplayTrackActivity!");

        Intent intent = getIntent();
        String searchWord = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        System.out.println("Searchword retrieved and is: " + searchWord);

        spotifyApiController = new SpotifyApiController();
        List<Track> tracks = spotifyApiController.getTracksList(searchWord);
        System.out.println("SIZE OF LIST OF TRACKS: " + tracks.size());
    }
}
