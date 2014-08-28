/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package echonest.v4;

import echonest.v4.*;
import echonest.v4.EchoNestException;
import echonest.v4.Params;
import echonest.v4.Playlist;
import echonest.v4.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Manages a dynamic playlist sessin
 *
 * @author plamere
 */
public class DynamicPlaylistSession {

    private echonest.v4.EchoNestAPI en;
    private String sessionID;

    /**
     * Feedback types
     */
    public enum FeedbackType {

        ban_artist, favorite_artist, ban_song, skip_song, favorite_song,
        play_song, unplay_song, rate_song
    };

    DynamicPlaylistSession(echonest.v4.EchoNestAPI en, String sessionID) {
        this.en = en;
        this.sessionID = sessionID;
    }

    /**
     * Restarts a playlist session. Given the session ID and a new set of
     * playlist parameters, this method restarts the playlist session based upon
     * the new parameters. The session history is maintained. Everything else is
     * reset.
     *
     * @param p
     * @throws echonest.v4.EchoNestException
     */
    public void restart(echonest.v4.Params p) throws echonest.v4.EchoNestException {
        Map results = en.getCmd().sendCommand("playlist/dynamic/restart", p);
        Map response = (Map) results.get("response");

        String newSessionID = (String) response.get("session_id");
        if (!sessionID.equals(newSessionID)) {
            System.err.println("unexpected sessionID change");
            sessionID = newSessionID;
        }
    }

    /**
     * Returns the next songs in the playlist. Results includes two lists of
     * songs - one list (called next) contains the next songs to play, the other
     * (called lookahead) contains the lookahead songs (controlled via the
     * lookahead parameter). The next songs returned by this method will be
     * considered to be played starting at the time the call returns. Use the
     * dynamic/feedback method to indicate that the song was skipped or not
     * played.
     *
     * @param results the desired number of next songs returned
     * @param lookahead lookahead songs to return. Lookahead songs are the next
     * songs that will be returned to be played if no user feedback or steering
     * occurs before the next dynamic/next method call.
     * @return the playlist
     * @throws echonest.v4.EchoNestException
     */
    public echonest.v4.Playlist next(int results, int lookahead) throws echonest.v4.EchoNestException {
        echonest.v4.Params p = new echonest.v4.Params();

        p.set("results", results);
        p.set("lookahead", lookahead);
        p.set("session_id", sessionID);

        Map cresults = en.getCmd().sendCommand("playlist/dynamic/next", p);
        Map response = (Map) cresults.get("response");

        String session = (String) response.get("session_id");


        List<echonest.v4.Song> songResults = new ArrayList<echonest.v4.Song>();

        List songList = (List) response.get("songs");
        for (int i = 0; i < songList.size(); i++) {
            echonest.v4.Song song = new echonest.v4.Song(en, (Map) songList.get(i));
            songResults.add(song);
        }

        List<echonest.v4.Song> lookaheadResults = new ArrayList<echonest.v4.Song>();
        List lookaheadList = (List) response.get("lookahead");
        for (int i = 0; i < lookaheadList.size(); i++) {
            echonest.v4.Song song = new Song(en, (Map) lookaheadList.get(i));
            lookaheadResults.add(song);
        }

        echonest.v4.Playlist playlist = new echonest.v4.Playlist(songResults, lookaheadResults, session);
        return playlist;
    }

    
    /**
     * Returns the next song in the playlist session
     * @return
     * @throws echonest.v4.EchoNestException
     */
    public Playlist next() throws echonest.v4.EchoNestException {
        return next(1, 0);
    }
    /**
     * Give feedback on the given items (artists or songs) or the last played
     * song. This method allows you to give user feedback such as rating,
     * favoriting, banning of a song or artist. Feedback is applied to the item
     * specified by the given song_id or track_id. If no song_id or track_id is
     * specified, then the feedback is applied to the most recent song returned
     * by dynamic/next. Multiple feedbacks can be given at any time. Specified
     * songs need not be in the session history. This allows the pre-seeding of
     * a session with played tracks, skipped tracks, favorites, ratings and
     * bans.
     *
     * @param type the type of feedback
     * @param id an artist song or track ID
     * @throws echonest.v4.EchoNestException
     */
    public void feedback(FeedbackType type, String id) throws echonest.v4.EchoNestException {
        echonest.v4.Params p = new echonest.v4.Params();
        p.set("session_id", sessionID);
        p.set(type.toString(), id);
        Map results = en.getCmd().sendCommand("playlist/dynamic/feedback", p);
    }

    /**
     * Give feedback on the given items (artists or songs) or the last played
     * song. This method allows you to give user feedback such as rating,
     * favoriting, banning of a song or artist. Feedback is applied to the item
     * specified by the given song_id or track_id. If no song_id or track_id is
     * specified, then the feedback is applied to the most recent song returned
     * by dynamic/next. Multiple feedbacks can be given at any time. Specified
     * songs need not be in the session history. This allows the pre-seeding of
     * a session with played tracks, skipped tracks, favorites, ratings and
     * bans.
     *
     * @param type the type of feedback
     * @param id a list of artist song or track IDs
     * @throws echonest.v4.EchoNestException
     */
    public void feedback(FeedbackType type, List<String> ids) throws echonest.v4.EchoNestException {
        echonest.v4.Params p = new echonest.v4.Params();
        p.set("session_id", sessionID);
        p.add(type.toString(), ids);
        Map results = en.getCmd().sendCommand("playlist/dynamic/feedback", p);
    }

    /**
     * Steers the playlist based upon the given constraints. Steerings are
     * additive and can be reset with the reset parameter.
     *
     * @param p steering parameters
     * @throws echonest.v4.EchoNestException
     */
    public void steer(echonest.v4.Params p) throws echonest.v4.EchoNestException {
        p.set("session_id", sessionID);
        Map results = en.getCmd().sendCommand("playlist/dynamic/steer", p);
    }

    public Map info() throws echonest.v4.EchoNestException {
        echonest.v4.Params p = new echonest.v4.Params();
        p.set("session_id", sessionID);
        Map results = en.getCmd().sendCommand("playlist/dynamic/feedback", p);
        return results;
    }

    public void delete() throws EchoNestException {
        echonest.v4.Params p = new Params();
        p.set("session_id", sessionID);
        Map results = en.getCmd().sendCommand("playlist/dynamic/delete", p);
    }
}
