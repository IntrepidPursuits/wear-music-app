package intrepid.weardemo.async;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import echonest.v4.Params;
import echonest.v4.Song;
import echonest.v4.SongParams;
import intrepid.weardemo.callbacks.EchonestTracksCallback;

public class SearchEchonestSongsTask extends AsyncTask<SongParams, Void, List<Song>> {

    EchonestTracksCallback callback;
    EchoNestAPI api;

    public SearchEchonestSongsTask(EchoNestAPI api, EchonestTracksCallback callback) {
        this.callback = callback;
        this.api = api;
    }

    @Override
    protected List<Song> doInBackground(SongParams... params) {
        SongParams songParams = params[0];
        List<Song> songs = null;
        try {
            songs = api.searchSongs(songParams);
        } catch (EchoNestException e) {
            Log.e("EN", e.getMessage());
        }
        return songs;
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);
        callback.tracksReturned(songs);
    }
}
