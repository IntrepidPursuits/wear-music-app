package echonest.v4.examples;

import echonest.v4.Artist;
import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import java.util.List;

public class SimlarArtists {

    public static void main(String[] args) throws EchoNestException {
        EchoNestAPI echoNest = new EchoNestAPI();
        echoNest.setTraceSends(true);
        List<Artist> artists = echoNest.searchArtists("Weezer");

        if (artists.size() > 0) {
            Artist weezer = artists.get(0);
            System.out.println("Similar artists for " + weezer.getName());
            for (Artist simArtist : weezer.getSimilar(10)) {
                System.out.println("   " + simArtist.getName());
            }
        }
    }
}
