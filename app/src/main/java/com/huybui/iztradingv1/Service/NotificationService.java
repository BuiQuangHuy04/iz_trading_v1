package com.huybui.iztradingv1.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.huybui.iztradingv1.Activity.MainActivity;
import com.huybui.iztradingv1.Application;
import com.huybui.iztradingv1.R;

public class NotificationService {

    public Context mContext;

    public NotificationService(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    public void sendNoti(String title, String body) {
        try {
            Intent intent = new Intent(mContext, MainActivity.class);
            PendingIntent pendingIntent;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_MUTABLE);
            } else  pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(mContext, Application.CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body));

            Notification notification = notiBuilder.build();

            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.notify(1, notification);
                System.out.println(notification);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
