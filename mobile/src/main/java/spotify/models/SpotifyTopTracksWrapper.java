package spotify.models;

import java.util.ArrayList;


public class SpotifyTopTracksWrapper {

    private ArrayList<SpotifyTrack> tracks;

    public ArrayList<SpotifyObject> getTracks() {
        ArrayList<SpotifyObject> tracks = new ArrayList<SpotifyObject>();
        for (SpotifyTrack track : this.tracks) {
            tracks.add(track);
        }
        return tracks;
    }

}
