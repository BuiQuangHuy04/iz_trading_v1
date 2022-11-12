package com.huybui.iztradingv1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class Application extends android.app.Application {

    public static final String CHANNEL_ID = "push_noti_id";

    @Override
    public void onCreate() {
        super.onCreate();

        createChannelNotification();
    }

    private void createChannelNotification() {
            Uri soundUri = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Signal", NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(soundUri, audioAttributes);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
