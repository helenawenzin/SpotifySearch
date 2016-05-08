package com.wenzin.helena.spotifysearch;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;

/**
 * Created by Helena on 2016-05-02.
 */
public class SpotifyApiController {

    private SpotifyApi api = new SpotifyApi();

    public List<Track> getTracksList(String searchWord) {

        List<Track> tracks = new ArrayList<>();

        if (searchWord == null || searchWord.equals("") ) {
            return tracks;
        }

        TracksPager tracksPager;

        Hashtable<String,String> params = new Hashtable<>();
        params.put("word", searchWord);
        params.put("offset", String.valueOf(0));

        try {
            SearchTracksTask searchTracksTask = new SearchTracksTask();
            tracksPager = searchTracksTask.execute(params).get();
            tracks = tracksPager.tracks.items;
            params.put("offset", String.valueOf(50));
            searchTracksTask = new SearchTracksTask();
            tracksPager = searchTracksTask.execute(params).get();
            tracks.addAll(tracksPager.tracks.items);
        } catch (InterruptedException e) {
            // TODO Implement exception handling
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Implement exception handling
            e.printStackTrace();
        }
        return tracks;
    }

    private class SearchTracksTask extends AsyncTask<Hashtable<String,String>, Void, TracksPager> {

        TracksPager tracks;

        @Override
        protected TracksPager doInBackground(Hashtable<String,String>... params) {

            Map<String, Object> map = new HashMap<>();
            map.put("offset", params[0].get("offset"));
            map.put("limit", 50);

            try {
               tracks = api.getService().searchTracks(params[0].get("word"), map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tracks;
        }
    }
}

