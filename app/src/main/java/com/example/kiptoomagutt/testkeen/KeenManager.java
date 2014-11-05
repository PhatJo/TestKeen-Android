package com.example.kiptoomagutt.testkeen;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.keen.client.android.AndroidKeenClientBuilder;
import io.keen.client.java.KeenCallback;
import io.keen.client.java.KeenClient;
import io.keen.client.java.KeenLogging;
import io.keen.client.java.KeenProject;

/**
 * Created by kiptoo.magutt on 11/5/14.
 */
public class KeenManager {
    private static String TAG = "KeenManager";
    private static Context mContext = MyApp.getContext();

    private static KeenClient mKeenClient;
    private static KeenProject mKeenProject;

    // keen.io
    private final String keenProjectId = "540a4d132481962d30612624";
    private final String keenWriteKey = "e9cc567a4e034c87cecdf6b063f6527cc9cc91ceb7171b1c3f3fb619" +
            "deb1044b3beeea0551222869d6b1c2e94d8ea0751408db8fe82549aa26a971a623a719ef4bec3235e8a9" +
            "69793e92e364348ee03d334c772fbbfb1df49075f360b61d54201578a9bbeb54f76433dc36f8f6d92c76";
    private final String keenReadKey = "9d19d258a6a98701bc98d298e6944a91eaded34e8c1ade3d6093b20ca" +
            "86k0e04e224aca10544727fa7dd59572f435682a79ed965694fcb9a7e2111844c962d143c20589023b69" +
            "a85c445a1e6ad155c15e5fc4a018d2f42114c171e23e270996fd5bce2f0155b1046a7bb695b352a35950";

    private static Map<String, Object> mSessionEventData;
    private static KeenManager keenManager = new KeenManager();

    private KeenManager() {
        mKeenClient = new AndroidKeenClientBuilder(mContext).build();
        mKeenProject = new KeenProject(keenProjectId, keenWriteKey, keenReadKey);
        mKeenClient.setDefaultProject(mKeenProject);
        KeenLogging.enableLogging();
    }

    public static KeenManager getInstance() {
        return keenManager;
    }

    public static void trackMyActivityCreated() {
        mSessionEventData = new HashMap<String, Object>();
        long activityStart =  System.currentTimeMillis() / 1000L;
        mSessionEventData.put("activity_started_at", activityStart);
        mKeenClient.queueEvent("activity_launches", mSessionEventData);
    }

    public static void trackMyActivityStopped() {
        KeenCallback keenCallBack = new KeenCallback () {
            @Override
            public void onFailure(Exception arg0) {
                Log.d(TAG, "error posting event: " + arg0.getMessage());
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "event posted successfully");
            }
        };
        long activityEnd =  System.currentTimeMillis() / 1000L;
        mSessionEventData.put("activity_closed_at", activityEnd);
        mKeenClient.sendQueuedEventsAsync(mKeenProject, keenCallBack);
    }
}
