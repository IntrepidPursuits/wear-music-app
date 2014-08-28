package intrepid.weardemo.async;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import echonest.v4.Artist;
import echonest.v4.ArtistParams;
import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import intrepid.weardemo.callbacks.EchonestSimilarArtistsCallback;

public class GetEchonestSimilarArtistsTask extends AsyncTask<ArtistParams, Void, List<Artist>> {

    EchonestSimilarArtistsCallback callback;
    EchoNestAPI api;

    public GetEchonestSimilarArtistsTask(EchoNestAPI api, EchonestSimilarArtistsCallback callback) {
        this.callback = callback;
        this.api = api;
    }

    @Override
    protected List<Artist> doInBackground(ArtistParams... params) {
        ArtistParams artistParams = params[0];

        List<Artist> similar = null;
        try {
            similar = api.getSimilarArtists(artistParams);
        } catch (EchoNestException e) {
            Log.e("Exception", e.getMessage());
        } catch (RuntimeException e) {
            Log.e("Runtime", e.getMessage());
        }

        return similar;
    }

    @Override
    protected void onPostExecute(List<Artist> artists) {
        super.onPostExecute(artists);
        callback.artistsReturned(artists);
    }
}
