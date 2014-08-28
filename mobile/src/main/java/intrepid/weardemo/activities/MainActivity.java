package intrepid.weardemo.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.sdk.android.Spotify;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.authentication.SpotifyAuthentication;
import com.spotify.sdk.android.playback.ConnectionStateCallback;
import com.spotify.sdk.android.playback.Player;
import com.spotify.sdk.android.playback.PlayerNotificationCallback;

import echonest.v4.Artist;
import echonest.v4.EchoNestAPI;
import echonest.v4.EchoNestException;
import echonest.v4.Song;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import echonest.v4.TrackAnalysis;
import intrepid.weardemo.Constants;
import intrepid.weardemo.CustomQueue;
import intrepid.weardemo.R;
import intrepid.weardemo.adapters.SongsAdapter;
import intrepid.weardemo.application.WearDemoApplication;
import gmusic.api.api.impl.InvalidCredentialsException;
import gmusic.api.api.model.Playlists;
import intrepid.weardemo.callbacks.EchonestSimilarArtistsCallback;
import intrepid.weardemo.callbacks.EchonestTracksCallback;
import intrepid.weardemo.services.WearableService;
import intrepid.weardemo.utils.CustomUtils;
import intrepid.weardemo.utils.EchonestUtils;
import intrepid.weardemo.utils.SpotifyUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import spotify.models.SpotifyArtist;
import spotify.models.SpotifyArtistPagingWrapper;
import spotify.models.SpotifyTopTracksWrapper;
import spotify.models.SpotifyTrack;

public class MainActivity extends Activity implements PlayerNotificationCallback,
        ConnectionStateCallback,
        EchonestTracksCallback,
        EchonestSimilarArtistsCallback,
        Callback<SpotifyTrack> {

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.hasExtra("heartRate")) {
                int message = intent.getIntExtra("heartRate", -1);
                // Get heart rate and display
                heartRate.setText(String.valueOf(message));
            } else if (intent.hasExtra("Track") || intent.hasExtra("Artist")) {
                String artistName = intent.getStringExtra("Artist");
                String trackName = intent.getStringExtra("Track");

                artist.setText(getResources().getString(R.string.artist) + " " + artistName);
                track.setText(getResources().getString(R.string.track) + " " + trackName);

                EchonestUtils.searchForTracksByArtist(echoNestAPI, trackName, artistName, MainActivity.this);
            }
        }
    }

    public interface EchonestCallback {
        public void finishedRetrievingAnalysis(TrackAnalysis analysis);
    }

    private Playlists playlists;
    private ServiceConnection connection;

    private TextView heartRate;
    private TextView artist;
    private TextView track;

    private TextView goButton;

    Intent service;

    private ListView listView;
    private SongsAdapter adapter;

    private Player mPlayer;
    EchoNestAPI echoNestAPI;

    private CustomQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        // Register a local broadcast receiver
        IntentFilter messageFilter = new IntentFilter("message-forwarded-from-data-layer");
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        queue = CustomQueue.createQueue();

//        getGoogleMusic();

        heartRate = (TextView) findViewById(R.id.heart_rate);
        goButton = (TextView) findViewById(R.id.go_button);

        track = (TextView) findViewById(R.id.track_name);
        artist = (TextView) findViewById(R.id.artist_name);

        service = new Intent(this, WearableService.class);
//        bindService(service, connection, Context.BIND_AUTO_CREATE);
        startService(service);

        adapter = new SongsAdapter(this, queue.getTracks());
        listView = (ListView) findViewById(R.id.echonest_info_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mPlayer != null) {
                    mPlayer.play(((SpotifyTrack) adapter.getItem(position)).getUri());
                }
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EchonestUtils.searchForTracksByArtist(echoNestAPI, "aerodynamic", "daft punk", MainActivity.this);
            }
        });

        setupEchonest();
        setupSpotify();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        stopService(service);
        try {
            unbindService(connection);
        } catch (IllegalArgumentException e) {
            Log.e("Connection", e.getMessage());
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    //================================================================================
    // Spotify
    //================================================================================

    private void setupSpotify() {
        SpotifyAuthentication.openAuthWindow(Constants.SPOTIFY_CLIENT_ID, "token", Constants.SPOTIFY_REDIRECT_URI, new String[]{"user-read-private", "streaming"}, null, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = SpotifyAuthentication.parseOauthResponse(uri);
            Spotify spotify = new Spotify(response.getAccessToken());

            Player.InitializationObserver observer = new Player.InitializationObserver() {
                @Override
                public void onInitialized() {
                    mPlayer.addConnectionStateCallback(MainActivity.this);
                    mPlayer.addPlayerNotificationCallback(MainActivity.this);
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                }
            };

            mPlayer = spotify.getPlayer(this, "Intrepid", this, observer);
        }
    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onNewCredentials(String s) {

    }

    @Override
    public void onConnectionMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlaybackEvent(EventType eventType) {

    }

    private void getArtist(String artistName) {
        WearDemoApplication.getSpotifyService().searchArtist(artistName, "artist", 0, new Callback<SpotifyArtistPagingWrapper>() {
            @Override
            public void success(SpotifyArtistPagingWrapper spotifyArtistPagingWrapper, Response response) {
                Log.d("Tag", "made it");
                getArtistTracks(((SpotifyArtist) spotifyArtistPagingWrapper.getArtists().getItems().get(0)).getId());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", error.getMessage());
            }
        });
    }

    private void getArtistTracks(String artistId) {
        WearDemoApplication.getSpotifyService().getArtistTopTracks(artistId, "US", new Callback<SpotifyTopTracksWrapper>() {
            @Override
            public void success(SpotifyTopTracksWrapper spotifyTopTracksWrapper, Response response) {
                Log.d("Got artists", "Artists");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Error", "err");
            }
        });
    }

    //================================================================================
    // Echonest
    //================================================================================

    private void setupEchonest() {
        echoNestAPI = new EchoNestAPI(getResources().getString(R.string.echonest_api_key));
    }

    private void updateQueue() {
        adapter.setValues(queue.getTracks());
        adapter.notifyDataSetChanged();
    }

    private void checkForNoSpotifyData() {
        if (!SpotifyUtils.getSpotifyTrack(queue.getCurrentlyIndexedSong(), MainActivity.this)) {
            queue.incrementSongChoiceIndex();
            checkForNoSpotifyData();
        }
    }

    private void checkForSimilarArtist(List<Artist> artists) {
        String currentArtistName = queue.getCurrentArtistName();
        String name = null;
        try {
            name = artists.get(queue.getArtistChoiceIndex()).getName();
        } catch (EchoNestException e) {
            Log.e("Artists", e.getMessage());
        } catch (NullPointerException e) {
            Log.e("NP", e.getMessage());
        }

        String s1 = CustomUtils.stripNonLetters(currentArtistName);
        String s2 = CustomUtils.stripNonLetters(name);

        if (s1.equals(s2) || queue.getArtistCount(name) >= 1 || name.contains("Various Artists")) {
            queue.incrementArtistChoiceIndex();
            checkForSimilarArtist(artists);
        } else {
            queue.updateArtistCount(name);
            EchonestUtils.searchForTracksByArtist(echoNestAPI, "", name, MainActivity.this);
        }
    }

    //================================================================================
    // Callbacks
    //================================================================================

    @Override
    public void tracksReturned(List<Song> songs) {
        queue.setCurrentENSongChoices(songs);
        checkForNoSpotifyData();
    }

    @Override
    public void artistsReturned(List<Artist> artists) {
        checkForSimilarArtist(artists);
    }

    @Override
    public void success(SpotifyTrack spotifyTrack, Response response) {
        queue.setCurrentTrack(spotifyTrack);

        if (queue.currentTrackIsLive() || queue.currentTrackExistsInQueue() || queue.currentTrackFromCompilation() || queue.notAvailableInUS()) {
            queue.incrementSongChoiceIndex();
            checkForNoSpotifyData();
        } else {
            queue.addCurrentTrackUriToList();
            queue.resetSongChoiceIndex();

            if (mPlayer != null && !mPlayer.isPlaying()) {
                mPlayer.play(queue.getCurrentTrackUri());
            }

            queue.addTrack(spotifyTrack);
            updateQueue();

            queue.incrementCurrentSize();
            if (queue.getSize() < 10) {
                EchonestUtils.getSimilarArtists(echoNestAPI, queue.getCurrentTrack(), MainActivity.this);
            }
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e("Error", error.getMessage());
    }

    //================================================================================
    // Google Play Music
    //================================================================================

    private void getGoogleMusic() {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    WearDemoApplication.getAPI().login("", "");
                    playlists = WearDemoApplication.getAPI().getAllPlaylists();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (InvalidCredentialsException e) {
                    e.printStackTrace();
                }
                Log.d("Derp", String.valueOf(playlists.getPlaylists().size()));

                return null;
            }
        };

        task.execute();
    }
}
