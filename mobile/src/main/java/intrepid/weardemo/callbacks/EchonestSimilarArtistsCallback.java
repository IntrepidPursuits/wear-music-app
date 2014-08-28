package intrepid.weardemo.callbacks;

import java.util.List;

import echonest.v4.Artist;

public interface EchonestSimilarArtistsCallback {
    public void artistsReturned(List<Artist> artists);
}
