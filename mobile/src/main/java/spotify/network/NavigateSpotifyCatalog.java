package spotify.network;

import java.util.ArrayList;

import intrepid.weardemo.application.WearDemoApplication;
import spotify.models.SpotifyObject;

public interface NavigateSpotifyCatalog {

    public void seeMultiFieldSearchResults();

    public void seeSingleFieldSearchResults(String searchQuery,
                                            ArrayList<SpotifyObject> results,
                                            WearDemoApplication.MusicField searchField,
                                            Boolean artistSet, Boolean onLastPage);

    public void seeSingleItemSearchResults(SpotifyObject spotifyObject,
                                           WearDemoApplication.MusicField searchField);

}
