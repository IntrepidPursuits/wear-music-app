package spotify.models;

import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;

import intrepid.weardemo.application.WearDemoApplication;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import spotify.Constants;
import spotify.network.SpotifyService;


public class SpotifyAlbumArrayList extends ArrayList<SpotifyObject> {

    private HashMap<String, AlbumEntry> albumMap;
    private ArrayList<SpotifyObject> albumsWithDuplicates;
    private SpotifyService spotifyService;
    private OnAlbumArrayUpdatedListener albumArrayUpdatedListener;
    private int numCycle;
    private int numAlbumsOutstanding;

    public SpotifyAlbumArrayList(OnAlbumArrayUpdatedListener albumArrayUpdatedListener) {
        albumMap = new HashMap<String, AlbumEntry>();
        albumsWithDuplicates = new ArrayList<SpotifyObject>();
        spotifyService = WearDemoApplication.getSpotifyService();
        this.albumArrayUpdatedListener = albumArrayUpdatedListener;
        numCycle = 0;
    }

    private class AlbumEntry {
        SpotifyAlbum album;
        int index;
    }

    public void addFiltered(ArrayList<SpotifyObject> newResults, boolean countryFilter) {
        albumsWithDuplicates.clear();
        if (countryFilter) {
            filterByCountryCode(newResults);
        } else {
            albumsWithDuplicates.addAll(newResults);
        }

        numAlbumsOutstanding = albumsWithDuplicates.size();
        if (numAlbumsOutstanding == 0) {
            numCycle += 1;
            albumArrayUpdatedListener.onAlbumArrayUpdateCompleted(getSearchOffset());
        } else {
            filterDuplicates();
        }
    }

    private void filterByCountryCode(ArrayList<SpotifyObject> newResults) {
        for (SpotifyObject album : newResults) {
            if (((SpotifyAlbum) album).getMarkets().contains(Constants.COUNTRY_CODE)) {
                albumsWithDuplicates.add(album);
            }
        }
    }

    private void filterDuplicates() {
        for (SpotifyObject spotifyAlbum : albumsWithDuplicates) {
            getAlbum(((SpotifyAlbum) spotifyAlbum).getId());
        }
    }

    private void getAlbum(final String albumId) {
        final String originalSearchQuery = albumArrayUpdatedListener.getSearchQuery();
        spotifyService.getAlbum(albumId, new Callback<SpotifyAlbum>() {
            @Override
            public void success(SpotifyAlbum spotifyAlbum, Response response) {
                if (originalSearchQuery.equals(albumArrayUpdatedListener.getSearchQuery())) {
                    addAlbumIfNotDuplicate(spotifyAlbum);

                    numAlbumsOutstanding -= 1;
                    if (numAlbumsOutstanding == 0) {
                        numCycle += 1;
                        albumArrayUpdatedListener.onAlbumArrayUpdateCompleted(getSearchOffset());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("feeld", "failed getting full album");
            }
        });
    }

    private void addAlbumIfNotDuplicate(SpotifyAlbum album) {
        String albumKey = album.getName() + album.getArtists().get(0).getId();
        AlbumEntry albumEntry;

        if ((albumEntry = albumMap.get(albumKey)) != null) {
            SpotifyAlbum duplicateAlbum = albumEntry.album;

            if (duplicateAlbum.getTracks().getTotal() < album.getTracks().getTotal()) {
                albumEntry.album = album;
                super.set(albumEntry.index, album);
            }
        } else {
            albumEntry = new AlbumEntry();
            albumEntry.album = album;
            albumEntry.index = super.size();

            albumMap.put(albumKey, albumEntry);
            super.add(album);
            albumArrayUpdatedListener.onAlbumArrayUpdated(album);
        }
    }

    public interface OnAlbumArrayUpdatedListener {

        public void onAlbumArrayUpdated(SpotifyAlbum spotifyAlbum);

        public void onAlbumArrayUpdateCompleted(int searchOffset);

        public String getSearchQuery();

    }

    public void setAlbumArrayUpdatedListener(OnAlbumArrayUpdatedListener albumArrayUpdatedListener) {
        this.albumArrayUpdatedListener = albumArrayUpdatedListener;
    }

    public int getSearchOffset() {
        return numCycle * Constants.NUM_RESULTS_RETURNED;
    }

    @Override
    public void clear() {
        super.clear();
        albumMap.clear();
        numCycle = 0;
    }

}
