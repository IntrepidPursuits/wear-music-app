package spotify.models;


import java.util.ArrayList;

import spotify.Constants;


public class SpotifyTrackArrayList extends ArrayList<SpotifyObject> {

    private OnTrackArrayUpdatedListener trackArrayUpdatedListener;
    private int numCycle;

    public SpotifyTrackArrayList(OnTrackArrayUpdatedListener trackArrayUpdatedListener) {
        this.trackArrayUpdatedListener = trackArrayUpdatedListener;
        numCycle = 0;
    }

    public void addFiltered(ArrayList<SpotifyObject> newResults) {
        filterByCountryCode(newResults);
        numCycle += 1;
        trackArrayUpdatedListener.onTrackArrayUpdateCompleted(getSearchOffset());
    }

    private void filterByCountryCode(ArrayList<SpotifyObject> newResults) {
        for (SpotifyObject track : newResults) {
            if (((SpotifyTrack) track).getMarkets().contains(Constants.COUNTRY_CODE)) {
                super.add(track);
                trackArrayUpdatedListener.onTrackArrayUpdated((SpotifyTrack) track);
            }
        }
    }

    public interface OnTrackArrayUpdatedListener {

        public void onTrackArrayUpdated(SpotifyTrack spotifyTrack);

        public void onTrackArrayUpdateCompleted(int searchOffset);

    }

    public void setTrackArrayUpdatedListener(OnTrackArrayUpdatedListener trackArrayUpdatedListener) {
        this.trackArrayUpdatedListener = trackArrayUpdatedListener;
    }

    public int getSearchOffset() {
        return numCycle * Constants.NUM_RESULTS_RETURNED;
    }

    @Override
    public void clear() {
        super.clear();
        numCycle = 0;
    }

}