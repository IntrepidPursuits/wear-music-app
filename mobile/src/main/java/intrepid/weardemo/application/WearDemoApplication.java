package intrepid.weardemo.application;

import android.app.Application;

import gmusic.api.api.impl.GoogleMusicAPI;
import retrofit.RestAdapter;
import spotify.network.SpotifyService;

public class WearDemoApplication extends Application {

    public static GoogleMusicAPI API = new GoogleMusicAPI();
    public static final String SPOTIFY_ENDPOINT = "https://api.spotify.com";

    public static enum MusicField {
        TRACK, ARTIST, ALBUM
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static GoogleMusicAPI getAPI() {
        return API;
    }

    private static final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
            SPOTIFY_ENDPOINT).setLogLevel(RestAdapter.LogLevel.FULL).build();

    public static final SpotifyService spotifyService = restAdapter.create(SpotifyService.class);

    public static SpotifyService getSpotifyService() {
        return spotifyService;
    }
}
