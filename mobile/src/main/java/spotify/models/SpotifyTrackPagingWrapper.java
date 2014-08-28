package spotify.models;

import java.util.ArrayList;


public class SpotifyTrackPagingWrapper {

    private SpotifyTrackPagingObject tracks;

    public SpotifyTrackPagingObject getTracks() {
        return tracks;
    }

    public class SpotifyTrackPagingObject {

        private String href;
        private ArrayList<SpotifyTrack> items;
        private String next;
        private String previous;
        private int total;

        public String getHref() {
            return href;
        }

        public ArrayList<SpotifyObject> getItems() {
            ArrayList<SpotifyObject> items = new ArrayList<SpotifyObject>();
            for (SpotifyTrack track : this.items) {
                items.add(track);
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
