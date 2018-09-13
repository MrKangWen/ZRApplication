package com.zhaorou.zrapplication.network.update;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.zhaorou.zrapplication.R;


/**
 * 兼容8.0，开启服务后5秒内在service内部调用startForeground()，否则崩溃异常。
 *
 * @author z
 */

public class ServiceInsidePublicNotification {
    private final static String TAG = ServiceInsidePublicNotification.class.getSimpleName();

    private Context context;
    private NotificationManager notificationManager = null;
    //
    private final String channelID = "1000000000010";
    private final String channelName = "channel_name_1";
    //

    public ServiceInsidePublicNotification(Context context) {
        this.context = context;
        initNotification();
    }

    private void initNotification() {
        this.notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);

    }

    private NotificationManager setNotificationManager() {
        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        return this.notificationManager;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder getNotificationChannel_26(String title, String content) {
        Notification.Builder builder = new Notification.Builder(this.context, channelID);

        builder.setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
        return builder;
    }

    private Notification.Builder getNotification_25(String title, String content) {
        return new Notification.Builder(this.context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }

    public Notification getNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationManager();
            Notification notification = getNotificationChannel_26
                    (title, content).build();
            //notificationManager.notify(1, notification);
            return notification;
        } else {
            Notification notification = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = getNotification_25(title, content).build();
            }
            //notificationManager.notify(1, notification);
            return notification;
        }
    }


}
