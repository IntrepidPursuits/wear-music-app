package intrepid.weardemo.async;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import echonest.v4.Song;
import echonest.v4.SongParams;
import intrepid.weardemo.callbacks.EchonestTracksCallback;

public class GetEchonestSimilarSongsTask extends AsyncTask<SongParams, Void, List<Song>> {

    EchonestTracksCallback callback;
    EchoNestAPI api;


    public GetEchonestSimilarSongsTask(EchoNestAPI api, EchonestTracksCallback callback) {
        this.callback = callback;
        this.api = api;
    }

    @Override
    protected List<Song> doInBackground(SongParams... params) {
        SongParams songParams = params[0];
        List<Song> songs = null;
        try {
            songs = api.similarSongs(songParams);
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
