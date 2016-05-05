package com.wenzin.helena.spotifysearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ArrayAdapter<Track> itemsAdapter = getTrackArrayAdapter(tracks);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

    }

    @NonNull
    private ArrayAdapter<Track> getTrackArrayAdapter(List<Track> tracks) {
        //Convert list of tracks to array of tracks
        Track[] trackArray = tracks.toArray(new Track[tracks.size()]);
        TrackAdapter adapter = new TrackAdapter(this, android.R.layout.simple_list_item_1, trackArray);
        return adapter;
    }
}
