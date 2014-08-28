package spotify.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import echonest.v4.Song;


public class SpotifyTrack extends SpotifyObject {

    private SpotifyAlbum album;
    private ArrayList<SpotifyArtist> artists;
    @SerializedName("available_markets")
    private ArrayList<String> markets;
    @SerializedName("duration_ms")
    private long durationMs;
    private String href;
    private String name;
    private String uri;

    public SpotifyAlbum getAlbum() {
        return album;
    }

    public ArrayList<SpotifyArtist> getArtists() {
        return artists;
    }

    public ArrayList<String> getMarkets() {
        return markets;
    }

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public long getDurationMs() {
        return durationMs;
    }

}
