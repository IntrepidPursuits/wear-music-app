package intrepid.weardemo.async;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import echonest.v4.Artist;
import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import echonest.v4.Song;
import echonest.v4.SongCatalog;
import echonest.v4.SongCatalogItem;
import intrepid.weardemo.callbacks.EchonestTracksCallback;

public class GetEchonestTracksTask extends AsyncTask<Artist, Void, List<Song>> {

    private EchonestTracksCallback callback;
    private EchoNestAPI api;

    public GetEchonestTracksTask(EchoNestAPI api, EchonestTracksCallback callback) {
        this.callback = callback;
        this.api = api;
    }

    @Override
    protected List<Song> doInBackground(Artist... params) {
        Artist artist = params[0];
        List<Song> songs = null;
        try {
            artist.fetchBucket("id:spotify");
            songs = artist.getSongs(0, 10);
            SongCatalog catalog = api.getSongCatalogByName("");
            List<SongCatalogItem> read = catalog.read();
        } catch (EchoNestException e) {
            Log.e("Tracks", e.getMessage());
        }
        return songs;
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);
        callback.tracksReturned(songs);
    }
}
