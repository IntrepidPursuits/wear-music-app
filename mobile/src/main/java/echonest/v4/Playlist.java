package echonest.v4;

import echonest.v4.*;

import java.util.List;

/**
 * Represents a playlist
 *
 * @author plamere
 *
 */
public class Playlist {

    private List<echonest.v4.Song> songs;
    private List<echonest.v4.Song> lookahead = null;
    private String session = null;

    public Playlist(List<echonest.v4.Song> songs) {
        this.songs = songs;
    }

    Playlist(List<echonest.v4.Song> songs, String session) {
        this.songs = songs;
        this.session = session;
    }

    Playlist(List<echonest.v4.Song> songs, List<echonest.v4.Song> lookahead, String session) {
        this.songs = songs;
        this.lookahead = lookahead;
        this.session = session;
    }

    /**
     * Gets the list of songs for the playlist
     *
     * @return the list of songs
     */
    public List<echonest.v4.Song> getSongs() {
        return songs;
    }

    /**
     * Gets the list of lookahead songs for the playlist
     *
     * @return the list of lookahead songs
     */
    public List<echonest.v4.Song> getLookahead() {
        return lookahead;
    }

    /**
     * Gets the session ID for the playlist. Only dynamic playlists have session
     * IDs
     *
     * @return the session ID
     */
    public String getSession() {
        return session;
    }
}
