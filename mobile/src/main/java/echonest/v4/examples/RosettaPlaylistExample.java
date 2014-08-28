/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package echonest.v4.examples;

import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import echonest.v4.Playlist;
import echonest.v4.PlaylistParams;
import echonest.v4.Song;
import echonest.v4.Track;

/**
 * This example shows how to create 'genre radio' and return
 * the Spotify and Rdio IDs for the tracks in the playlt.
 * 
 * @author plamere
 */
public class RosettaPlaylistExample {
    
    public static void main(String[] args) throws EchoNestException {
        EchoNestAPI en = new EchoNestAPI();
        PlaylistParams params = new PlaylistParams();
        params.addIDSpace("spotify-WW");
        params.setType(PlaylistParams.PlaylistType.GENRE_RADIO);
        params.addGenre("dance pop");
        params.includeTracks();
        params.setLimit(true);
        
        Playlist playlist = en.createStaticPlaylist(params);

        for (Song song : playlist.getSongs()) {
            Track track = song.getTrack("spotify-WW");
            System.out.println(track.getForeignID() + " " + song.getTitle() + " by " + song.getArtistName());
        }
    }
}
  
