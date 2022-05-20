package com.example.android_tkpm.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.example.android_tkpm.CartActivity;
import com.example.android_tkpm.MainActivity;
import com.example.android_tkpm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationService extends FirebaseMessagingService {

    String token;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        RemoteViews lay_one = new RemoteViews(getPackageName(), R.layout.custom_notifications);
        String title = message.getNotification().getTitle();
        String body = message.getNotification().getBody();

        lay_one.setTextViewText(R.id.title_notifications, title);
        lay_one.setTextViewText(R.id.body_notifications, body);

//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("Active", "NotifyFragment");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Heads_Up_Notification",
                NotificationManager.IMPORTANCE_HIGH
        );
        getSystemService(NotificationManager.class).createNotificationChannel(channel);

        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setCustomContentView(lay_one)
                .setCustomBigContentView(lay_one)
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent)
                .setAutoCancel(false);

        NotificationManagerCompat.from(this).notify(1, notification.build());

        super.onMessageReceived(message);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        Log.e("TOKEN: ", token);
        getSharedPreferences("TOKEN_DEVICE", MODE_PRIVATE).edit().putString("token_device", token).apply();
//        sendRegistrationToServer(token);
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("TOKEN_DEVICE", MODE_PRIVATE).getString("token_device", "");
    }
}
