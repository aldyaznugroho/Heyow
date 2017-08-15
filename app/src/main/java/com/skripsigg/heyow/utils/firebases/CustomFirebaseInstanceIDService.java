package com.skripsigg.heyow.utils.firebases;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Aldyaz on 12/10/2016.
 */

public class CustomFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private final String TAG = getClass().getSimpleName();

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onTokenRefresh() {
        // Get update InstanceID token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String refreshedId = FirebaseInstanceId.getInstance().getId();
        Log.d(TAG, "Refreshed Token: " + refreshedToken);
        Log.d(TAG, "Refreshed ID: " + refreshedId);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token
     */
    public void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
