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
import echonest.v4.Song.SongType;

/**
 *
 * @author plamere
 */
public class ChristmasPlaylist {
    
        public static void main(String[] args) throws EchoNestException {
        EchoNestAPI en = new EchoNestAPI();

        PlaylistParams params = new PlaylistParams();
        params.setType(PlaylistParams.PlaylistType.ARTIST_RADIO);
        params.addArtist("Bing Crosby");
        params.addSongType(SongType.christmas, Song.SongTypeFlag.True);
        Playlist playlist = en.createStaticPlaylist(params);
        for (Song song : playlist.getSongs()) {
            System.out.println(song.getTitle() + " by " + song.getArtistName());
        }
    }
    
}
