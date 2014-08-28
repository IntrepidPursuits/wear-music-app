package spotify.models;

import java.util.ArrayList;


public class SpotifyArtistPagingWrapper {

    private SpotifyArtistPagingObject artists;

    public SpotifyArtistPagingObject getArtists() {
        return artists;
    }

    public class SpotifyArtistPagingObject {

        private String href;
        private ArrayList<SpotifyArtist> items;
        private String next;
        private String previous;
        private int total;

        public String getHref() {
            return href;
        }

        public ArrayList<SpotifyObject> getItems() {
            ArrayList<SpotifyObject> items = new ArrayList<SpotifyObject>();
            for (SpotifyArtist artist : this.items) {
                items.add(artist);
            }
            return items;
        }

        public String getNext() {
            return next;
        }

        public String getPrevious() {
            return previous;
        }

        public int getTotal() {
            return total;
        }

    }

}
