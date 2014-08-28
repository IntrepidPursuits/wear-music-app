package intrepid.weardemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import echonest.v4.Song;
import echonest.v4.TrackAnalysis;
import intrepid.weardemo.R;
import spotify.models.SpotifyTrack;

public class SongsAdapter extends BaseAdapter {

    private Context context;
    private List<SpotifyTrack> values;

    public SongsAdapter(Context context, List<SpotifyTrack> values) {
        this.context = context;
        this.values = values;
    }

    private class ViewHolder {
        TextView trackName;
        TextView bpm;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.songs_list_row, parent, false);
            holder = new ViewHolder();
            holder.trackName = (TextView) view.findViewById(R.id.track_name);
            holder.bpm = (TextView) view.findViewById(R.id.song_bpm);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SpotifyTrack track = values.get(position);
        holder.trackName.setText(track.getName());
        holder.bpm.setText(track.getArtists().get(0).getName());

        return view;
    }

    public void setValues(List<SpotifyTrack> values) {
        this.values = values;
    }

}
