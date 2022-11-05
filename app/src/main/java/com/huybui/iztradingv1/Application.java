package com.huybui.iztradingv1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Application extends android.app.Application {

    public static final String CHANNEL_ID = "push_noti_id";

    @Override
    public void onCreate() {
        super.onCreate();

        createChannelNotification();
    }

    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Signal", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
