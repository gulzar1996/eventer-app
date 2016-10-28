package com.eventer.app.Messaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.eventer.app.MainActivity;
import com.eventer.app.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by gaurav on 25/10/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message = remoteMessage.getData().get("message");

        showNotification(message);
    }
    private void showNotification(String message){

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, MainActivity.class);
        // Send data to NotificationView Class
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getBaseContext());
        NotificationCompat.BigTextStyle style =
                new NotificationCompat.BigTextStyle(builder);
        style.bigText(message /* long text goes here */ )
                .setBigContentTitle("Eventer")
                .setSummaryText("Crazy Labs");
        Notification notification = builder
                .setContentTitle("Eventer")
                .setContentText(message)
                .setSmallIcon(R.drawable.gradient_vertical)
                .setContentIntent(pIntent)
                .build();

        // Create Notification Manager
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getBaseContext());

        notificationManager.notify(0x1234, notification);

    }
}
