package com.wenzin.helena.spotifysearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                Track itemValue  = (Track) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue.artists.get(0).name , Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

    @NonNull
    private ArrayAdapter<Track> getTrackArrayAdapter(List<Track> tracks) {
        //Convert list of tracks to array of tracks
        Track[] trackArray = tracks.toArray(new Track[tracks.size()]);
        TrackAdapter adapter = new TrackAdapter(this, android.R.layout.simple_list_item_1, trackArray);
        return adapter;
    }
}
