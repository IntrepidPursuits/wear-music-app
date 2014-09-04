package intrepid.weardemo;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class WearMainActivity extends Activity implements SensorEventListener {

    private static int VOICE_RECOGNITION_REQUEST_CODE = 1;

    private TextView heartRate;
    private ImageButton voiceButton;
    private int startValue = 120;
    public static String TAG = "wear";

    GoogleApiClient mGoogleAppiClient;
    SensorManager mSensorManager;
    Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        mGoogleAppiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                        // Now you can use the data layer API
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                heartRate = (TextView) stub.findViewById(R.id.heart_rate);
                heartRate.setText(String.valueOf(startValue));
                voiceButton = (ImageButton) stub.findViewById(R.id.voice_button);
                voiceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        sendVoiceInfo("Saturdays", "Cut Copy");
                        startVoiceRecognition();
                    }
                });
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(65562); //Higher accuracy than default sensor

        GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        WearPagerAdapter adapter = new WearPagerAdapter(getFragmentManager());
        pager.setAdapter(adapter);
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * Responds to "play <song> by <artist>"
     */
    private void parseVoiceData(List<String> words) {
        String voiceText = words.get(0);
        String[] wordsArray = voiceText.split(" ");

        int index;
        StringBuilder trackBuilder = new StringBuilder();
        StringBuilder artistBuilder = new StringBuilder();

        if (wordsArray[0].equals("play")) {
            index = 1;
            while (index < wordsArray.length && !wordsArray[index].equals("by")) {
                trackBuilder.append(wordsArray[index]).append(" ");
                index++;
            }

            if (index != wordsArray.length) {
                index++; //skip the "by" keyword
                while (index < wordsArray.length) {
                    artistBuilder.append(wordsArray[index]).append(" ");
                    index++;
                }
            }
        }

        String trackName = trackBuilder.toString().trim();
        String artistName = artistBuilder.toString().trim();

        sendVoiceInfo(trackName, artistName);
    }

    private void sendVoiceInfo(String trackName, String artistName) {
        PutDataMapRequest mapRequest = PutDataMapRequest.create("/count");
        mapRequest.getDataMap().putString("Track", trackName);
        if (artistName != null) {
            mapRequest.getDataMap().putString("Artist", artistName);
        }

        PutDataRequest request = mapRequest.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleAppiClient, request);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {
//                Log.d("A", "Thing");
            }
        });
    }

    private void sendHeartRate(int heartRate) {
        PutDataMapRequest mapRequest = PutDataMapRequest.create("/count");
        mapRequest.getDataMap().putInt("HR", heartRate);

        PutDataRequest request = mapRequest.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleAppiClient, request);
        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(DataApi.DataItemResult dataItemResult) {
//                Log.d("A", "Thing");
            }
        });
//        Wearable.MessageApi.sendMessage(mGoogleAppiClient, "", "", new byte[3]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleAppiClient.connect();
        mSensorManager.registerListener(this, mSensor, 30 * 1000000);
    }

    @Override
    protected void onStop() {
        mGoogleAppiClient.disconnect();
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int heartRate = (int) event.values[0];
        sendHeartRate(heartRate);
        this.heartRate.setText(String.valueOf(heartRate));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE) {
            List<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            parseVoiceData(matches);
        }
    }
}
