package intrepid.weardemo.services;

import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WearableService extends WearableListenerService {

    private static final String TAG = "DataLayerSample";
    private static final String START_ACTIVITY_PATH = "/start-activity";
    private static final String DATA_ITEM_RECEIVED_PATH = "/count";

    public ResultReceiver mReceiver;

    public WearableService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        final List<DataEvent> events = FreezableUtils
                .freezeIterable(dataEvents);

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

        ConnectionResult connectionResult =
                googleApiClient.blockingConnect(30, TimeUnit.SECONDS);

        if (!connectionResult.isSuccess()) {
            Log.e(TAG, "Failed to connect to GoogleApiClient.");
            return;
        }

        // Loop through the events and send a message
        // to the node that created the data item.
        for (DataEvent event : events) {
            Uri uri = event.getDataItem().getUri();

            DataMapItem item = DataMapItem.fromDataItem(event.getDataItem());
            DataMap map = item.getDataMap();
            Boolean containsKey = map.containsKey("HR");
            if (containsKey) {
                int heartRate = map.getInt("HR");

                Log.d("HR", String.valueOf(heartRate));

                // Broadcast message to wearable activity for display
                Intent messageIntent = new Intent();
                messageIntent.setAction("message-forwarded-from-data-layer");
                messageIntent.putExtra("heartRate", heartRate);
                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
            } else if (map.containsKey("Track") || map.containsKey("Artist")) {
                String trackName = map.getString("Track");
                String artistName = map.getString("Artist");

                Intent messageIntent = new Intent();
                messageIntent.setAction("message-forwarded-from-data-layer");
                messageIntent.putExtra("Track", trackName);
                messageIntent.putExtra("Artist", artistName);
                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
            }

//            Intent returnIntent = new Intent("SERVICE");
//            returnIntent.putExtra("HR", heartRate);
//            sendBroadcast(returnIntent);


            // Get the node id from the host value of the URI
//            String nodeId = uri.getHost();
//            // Set the data of the message to be the bytes of the URI.
//            byte[] payload = uri.toString().getBytes();
//
//            // Send the RPC
//            Wearable.MessageApi.sendMessage(googleApiClient, nodeId,
//                    DATA_ITEM_RECEIVED_PATH, payload);
        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
    }

}
