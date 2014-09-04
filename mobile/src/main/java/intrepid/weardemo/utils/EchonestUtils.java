package intrepid.weardemo.utils;

import android.text.TextUtils;

import echonest.v4.ArtistParams;
import echonest.v4.EchoNestAPI;
import echonest.v4.Song;
import echonest.v4.SongParams;
import intrepid.weardemo.async.GetEchonestSimilarArtistsTask;
import intrepid.weardemo.async.GetEchonestSimilarSongsTask;
import intrepid.weardemo.async.SearchEchonestSongsTask;
import intrepid.weardemo.callbacks.EchonestSimilarArtistsCallback;
import intrepid.weardemo.callbacks.EchonestTracksCallback;
import spotify.models.SpotifyTrack;

public class EchonestUtils {

    public static void searchForTracksByArtist(EchoNestAPI echoNestAPI, String track, String artist, EchonestTracksCallback callback) {
        SearchEchonestSongsTask searchTask = new SearchEchonestSongsTask(echoNestAPI, callback);

        SongParams params = new SongParams();
        if (!TextUtils.isEmpty(track)) {
            params.setTitle(track);
        }

        if (!TextUtils.isEmpty(artist)) {
            params.setArtist(artist);
        }
        params.addSongType(Song.SongType.live, Song.SongTypeFlag.False);
        params.includeSongHotttnesss();
        params.add("bucket", new String[]{"id:spotify-WW", "tracks"});
//        params.setMinSongHotttnesss(0.2f);
//        params.requireDescription(EchoNestAPI.DescriptionType.general, "style");
//        params.banDescription(EchoNestAPI.DescriptionType.general, "live");

        searchTask.execute(params);
    }

    public static void getSimilarSongs(EchoNestAPI echoNestAPI, String track, String artist, EchonestTracksCallback callback) {
        GetEchonestSimilarSongsTask task = new GetEchonestSimilarSongsTask(echoNestAPI, callback);

        SongParams params = new SongParams();
//        params.setTitle(track);
//        params.setArtist(artist);
        params.setID(track);
//        params.setArtistID(artist);
//        params.includeSongHotttnesss();
//        params.add("bucket", new String[]{"id:spotify"/*, "tracks"*/});

        task.execute(params);
    }

    public static void getSimilarArtists(EchoNestAPI echoNestAPI, SpotifyTrack currentTrack, EchonestSimilarArtistsCallback callback) {
        GetEchonestSimilarArtistsTask task = new GetEchonestSimilarArtistsTask(echoNestAPI, callback);

        ArtistParams params = new ArtistParams();
        params.setName(currentTrack.getArtists().get(0).getName());

        task.execute(params);
    }
}
