package com.skripsigg.heyow.utils.firebases;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.utils.others.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by Aldyaz on 12/10/2016.
 */

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    private final String TAG = getClass().getSimpleName();

    /**
     * Called when message is received.
     * @param remoteMessage Object representing the message received from FCM.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (isAppIsInBackground(getApplicationContext())) {
            sendNotification(remoteMessage.getData());
        } else {
            Log.e(TAG, "App is in foreground");
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     * @param dataPayload
     */
    public void sendNotification(Map<String, String> dataPayload) {
        String messageTitle = dataPayload.get("title");
        String messageBody = dataPayload.get("message");
        String messageExtra = dataPayload.get("match_id");

        Bundle notifBundle = new Bundle();
        notifBundle.putString(Constants.KEY_MATCH_ID, messageExtra);

        Intent notificationIntent = new Intent(this, MatchDetailActivity.class);
        notificationIntent.putExtras(notifBundle);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.heyow_logo)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());
    }

    private void playNotificationSound() {
        try {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(this, defaultSoundUri);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcess = activityManager.getRunningAppProcesses();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcess) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
            ComponentName componentName = taskInfo.get(0).topActivity;
            if (componentName.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }
}
