package com.wenzin.helena.spotifysearch;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import kaaes.spotify.webapi.android.models.Track;

public class TrackAdapter extends ArrayAdapter<Track> {

    public TrackAdapter(Context context, int resource, Track[] tracks) {
        super(context, resource, tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        Track item = getItem(position);
        TextView textView = (TextView)view;
        textView.setText("Artist: " + item.artists.get(0).name + "  Song: " + item.name);
        return view;
    }

}
