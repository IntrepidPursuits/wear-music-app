package spotify.models;

import java.util.ArrayList;


public class SpotifyAlbumPagingWrapper {

    private SpotifyAlbumPagingObject albums;

    public SpotifyAlbumPagingObject getAlbums() {
        return albums;
    }

    public class SpotifyAlbumPagingObject {

        private String href;
        private ArrayList<SpotifyAlbum> items;
        private String next;
        private String previous;
        private int total;

        public String getHref() {
            return href;
        }

        public ArrayList<SpotifyObject> getItems() {
            ArrayList<SpotifyObject> items = new ArrayList<SpotifyObject>();
            for (SpotifyAlbum album : this.items) {
                items.add(album);
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
