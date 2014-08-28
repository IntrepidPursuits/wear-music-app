/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package echonest.v4.examples;

import echonest.v4.BasicPlaylistParams;
import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import echonest.v4.Playlist;
import echonest.v4.Song;

/**
 *
 * @author plamere
 */
public class BasicPlaylistExample {

    public static void main(String[] args) throws EchoNestException {
        EchoNestAPI en = new EchoNestAPI();

        BasicPlaylistParams params = new BasicPlaylistParams();
        params.addArtist("Weezer");
        params.setType(BasicPlaylistParams.PlaylistType.ARTIST_RADIO);
        params.setResults(10);
        Playlist playlist = en.createBasicPlaylist(params);

        for (Song song : playlist.getSongs()) {
            System.out.println(song.toString());
        }
    }
}
