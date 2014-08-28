/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package echonest.v4.examples;

import echonest.v4.Artist;
import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import echonest.v4.Song;
import echonest.v4.SongParams;
import echonest.v4.YearsActive;
import java.util.List;

/**
 *
 * @author plamere
 */
public class TopHotttestSongs {

    public static void main(String[] args) throws EchoNestException {
        EchoNestAPI en = new EchoNestAPI();

        SongParams p = new SongParams();
        p.setResults(100);
        p.sortBy("song_hotttnesss", false);
        List<Song> songs = en.searchSongs(p);


        String lastTitle = "";
        for (Song song : songs) {
            if (!lastTitle.toLowerCase().equals(song.getTitle().toLowerCase())) {
                System.out.println(song.getTitle() + " by " + song.getArtistName());
            }
            lastTitle = song.getTitle();
        }
    }
}
