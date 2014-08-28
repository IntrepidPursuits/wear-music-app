package intrepid.weardemo.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import echonest.v4.Artist;
import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import echonest.v4.Song;
import intrepid.weardemo.R;
import intrepid.weardemo.activities.MainActivity;
import intrepid.weardemo.callbacks.EchonestArtistsCallback;

public class GetEchonestArtistsTask extends AsyncTask<Void, Void, List<Artist>> {

    private Context context;
    private EchonestArtistsCallback callback;
    private EchoNestAPI echoNestAPI;

    public GetEchonestArtistsTask(Context context, EchoNestAPI echoNestAPI, EchonestArtistsCallback callback) {
        this.context = context;
        this.callback = callback;
        this.echoNestAPI = echoNestAPI;
    }

    @Override
    protected List<Artist> doInBackground(Void... params) {
        List<Artist> artists = null;
        try {
            artists = echoNestAPI.searchArtists("The Knocks");
        } catch (EchoNestException e) {
            Log.e("Error", e.getMessage());
        }
        return artists;

    }

    @Override
    protected void onPostExecute(List<Artist> artists) {
        super.onPostExecute(artists);
        callback.artistsReturned(artists);
    }
}
