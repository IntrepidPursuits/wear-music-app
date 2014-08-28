package intrepid.weardemo.utils;

import android.util.Log;

import echonest.v4.Song;
import intrepid.weardemo.application.WearDemoApplication;
import retrofit.Callback;
import spotify.models.SpotifyTrack;

public class SpotifyUtils {
    public static boolean getSpotifyTrack(Song song, Callback<SpotifyTrack> callback) {
        String spotifyId;
        try {
            spotifyId = song.getSpotifyUri();
        } catch (java.lang.IndexOutOfBoundsException e) {
            Log.e("exc", e.getMessage());
            return false;
        }

        WearDemoApplication.getSpotifyService().getTrack(spotifyId, callback);
        return true;
    }
}
