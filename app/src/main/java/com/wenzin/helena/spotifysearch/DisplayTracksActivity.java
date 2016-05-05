package com.wenzin.helena.spotifysearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import java.util.List;

import kaaes.spotify.webapi.android.models.Track;

public class DisplayTracksActivity extends AppCompatActivity implements PlayerNotificationCallback {

    private SpotifyApiController spotifyApiController;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tracks);

        System.out.println("I have entered DisplayTrackActivity!");

        Intent intent = getIntent();
        String searchWord = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String accessToken = intent.getStringExtra(MainActivity.ACCESS_TOKEN_MESSAGE);

        player = createPlayer(accessToken);

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

                // ListView Clicked item value
                Track track  = (Track) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Now playing: " +track.artists.get(0).name + " - " + track.name, Toast.LENGTH_SHORT)
                        .show();

                player.play(track.uri);
            }
        });
    }

    private Player createPlayer(String accessToken) {
        Config playerConfig = new Config(this, accessToken, MainActivity.CLIENT_ID);
        player = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                //mPlayer = player;
                //mPlayer.addConnectionStateCallback(MainActivity.this);
                //mPlayer.addPlayerNotificationCallback(MainActivity.this);
                //mPlayer.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
            }


            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
        return player;
    }

    @NonNull
    private ArrayAdapter<Track> getTrackArrayAdapter(List<Track> tracks) {
        //Convert list of tracks to array of tracks
        Track[] trackArray = tracks.toArray(new Track[tracks.size()]);
        TrackAdapter adapter = new TrackAdapter(this, android.R.layout.simple_list_item_1, trackArray);
        return adapter;
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("DisplayTracksActivity", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {
        Log.d("DisplayTracksActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}
