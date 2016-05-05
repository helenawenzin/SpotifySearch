package com.wenzin.helena.spotifysearch;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TrackSimple;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;

/**
 * Created by Helena on 2016-05-02.
 */
public class SpotifyApiController {

    private SpotifyApi api = new SpotifyApi();

  //  public SpotifyApiController(String accessToken) {
  //      api.setAccessToken(accessToken);
  //  }

   /* public List<TrackSimple> getTracksList(final String searchWord) {

        List<TrackSimple> trackList;

        new Thread(new Runnable() {
            public void run() {
                //Do whatever

                 trackList = (List<TrackSimple>) api.getService().getTracks(searchWord);

            }
        }).start();
        return trackList;
    }*/


    public List<Track> getTracksList(String searchWord) {

        TracksPager result;
        List<Track> tracks = new ArrayList<>();
        String nonsens = "nonsens";
        Hashtable<String,String> params = new Hashtable<String,String>();
        params.put("word", searchWord);
        params.put("nonsens", nonsens);

        GogetTracks goGetTracksFromSpotify = new GogetTracks();

        try {
            result = goGetTracksFromSpotify.execute(params).get();
            tracks = result.tracks.items;
        } catch (InterruptedException e) {
            // TODO Implement exception handling
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Implement exception handling
            e.printStackTrace();
        }

        return tracks;
    }

    private class GogetTracks extends AsyncTask<Hashtable<String,String>, Void, TracksPager> {

        TracksPager tracks;

        @Override
        protected TracksPager doInBackground(Hashtable<String,String>... params) {

            try {
               tracks = api.getService().searchTracks(params[0].get("word"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("STATUS FOR TRACKS = " + tracks.toString());
            return tracks;
        }

    }

}

