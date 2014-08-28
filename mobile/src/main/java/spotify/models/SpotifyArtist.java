package spotify.models;


import java.util.ArrayList;

public class SpotifyArtist extends SpotifyObject {

    private String href;
    private String name;
    private String id;
    private ArrayList<SpotifyImage> images;

    public String getHref() {
        return href;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ArrayList<SpotifyImage> getImages() {
        return images;
    }

}
