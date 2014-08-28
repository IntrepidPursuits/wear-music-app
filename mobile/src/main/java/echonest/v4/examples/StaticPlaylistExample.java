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

/**
 *
 * @author plamere
 */
public class StaticPlaylistExample {
    public static void main(String[] args) throws EchoNestException {
        EchoNestAPI en = new EchoNestAPI();

        // play fast songs by metal bands

        PlaylistParams params = new PlaylistParams();
        params.setType(PlaylistParams.PlaylistType.GENRE_RADIO);
        params.addGenre("metal");
        params.setMinTempo(150);
        params.setResults(10);
        params.includeAudioSummary();
        Playlist playlist = en.createStaticPlaylist(params);

        for (Song song : playlist.getSongs()) {
            System.out.println(song.toString());
        }
    }
}
