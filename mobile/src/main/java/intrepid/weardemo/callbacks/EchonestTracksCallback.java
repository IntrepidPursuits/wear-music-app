package intrepid.weardemo.callbacks;

import java.util.List;

import echonest.v4.Song;

public interface EchonestTracksCallback {
    public void tracksReturned(List<Song> songs);
}
