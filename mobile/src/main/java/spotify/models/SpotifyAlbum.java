package spotify.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class SpotifyAlbum extends SpotifyObject {

    @SerializedName("available_markets")
    private ArrayList<String> markets;
    private ArrayList<SpotifyArtist> artists;
    private ArrayList<String> genres;
    private String href;
    private ArrayList<SpotifyImage> images;
    private String name;
    private SpotifyTrackPagingWrapper.SpotifyTrackPagingObject tracks;
    private String id;
    @SerializedName("release_date")
    private String releaseDate;

    public ArrayList<String> getMarkets() {
        return markets;
    }

    public ArrayList<SpotifyArtist> getArtists() {
        return artists;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getHref() {
        return href;
    }

    public ArrayList<SpotifyImage> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public SpotifyTrackPagingWrapper.SpotifyTrackPagingObject getTracks() {
        return tracks;
    }

    public String getId() {
        return id;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
