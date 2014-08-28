package intrepid.weardemo.async;

import android.os.AsyncTask;
import android.util.Log;

import echonest.v4.EchoNestException;
import echonest.v4.Song;
import echonest.v4.TrackAnalysis;
import intrepid.weardemo.activities.MainActivity;

public class GetEchonestTrackAnalysisTask extends AsyncTask<Song, Void, TrackAnalysis> {

    private MainActivity.EchonestCallback callback;

    public GetEchonestTrackAnalysisTask(MainActivity.EchonestCallback callback) {
        this.callback = callback;
    }

    @Override
    protected TrackAnalysis doInBackground(Song... params) {
        Song song = params[0];
        TrackAnalysis analysis = null;
        try {
            analysis = song.getAnalysis();
        } catch (EchoNestException e) {
            Log.e("Error", e.getMessage());
        }
        return analysis;
    }

    @Override
    protected void onPostExecute(TrackAnalysis trackAnalysis) {
        super.onPostExecute(trackAnalysis);
        callback.finishedRetrievingAnalysis(trackAnalysis);
    }
}
