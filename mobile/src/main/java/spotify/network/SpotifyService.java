package spotify.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import spotify.models.SpotifyAlbum;
import spotify.models.SpotifyAlbumPagingWrapper;
import spotify.models.SpotifyArtistPagingWrapper;
import spotify.models.SpotifyTopTracksWrapper;
import spotify.models.SpotifyTrack;
import spotify.models.SpotifyTrackPagingWrapper;


public interface SpotifyService {

    @GET("/v1/search")
    public void searchAlbum(@Query("q") String query,
                            @Query("type") String type,
                            @Query("offset") int offset,
                            Callback<SpotifyAlbumPagingWrapper> callback);

    @GET("/v1/search")
    public void searchArtist(@Query("q") String query,
                             @Query("type") String type,
                             @Query("offset") int offset,
                             Callback<SpotifyArtistPagingWrapper> callback);

    @GET("/v1/search")
    public void searchTrack(@Query("q") String query,
                            @Query("type") String type,
                            @Query("offset") int offset,
                            Callback<SpotifyTrackPagingWrapper> callback);

    @GET("/v1/albums/{albumId}")
    public void getAlbum(@Path("albumId") String albumId,
                         Callback<SpotifyAlbum> callback);

    @GET("/v1/artists/{artistId}/albums")
    public void getArtistAlbums(@Path("artistId") String artistId,
                                @Query("country") String countryCode,
                                @Query("offset") int offset,
                                Callback<SpotifyAlbumPagingWrapper.SpotifyAlbumPagingObject> callback);

    @GET("/v1/artists/{artistId}/top-tracks")
    public void getArtistTopTracks(@Path("artistId") String artistId,
                                   @Query("country") String countryCode,
                                   Callback<SpotifyTopTracksWrapper> callback);

    @GET("/v1/tracks/{trackId}")
    public void getTrack(@Path("trackId") String trackId, Callback<SpotifyTrack> callback);

}
