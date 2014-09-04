package intrepid.weardemo.utils;

import android.util.Log;

import java.util.Map;

import echonest.v4.Song;
import echonest.v4.Track;
import intrepid.weardemo.application.WearDemoApplication;
import retrofit.Callback;
import spotify.models.SpotifyTrack;

public class SpotifyUtils {
    public static boolean getSpotifyTrack(Song song, Callback<SpotifyTrack> callback) {
        String spotifyId;
        try {
            Map<String, Track> trackMap = song.getTrackMap();
            spotifyId = song.getSpotifyUri(0);
        } catch (java.lang.IndexOutOfBoundsException e) {
            Log.e("exc", e.getMessage());
            return false;
        }

        WearDemoApplication.getSpotifyService().getTrack(spotifyId, callback);
        return true;
    }
}
