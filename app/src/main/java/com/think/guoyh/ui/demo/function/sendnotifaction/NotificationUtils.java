package com.think.guoyh.ui.demo.function.sendnotifaction;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.think.guoyh.R;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    private static final String id = "channel_1";
    private static final String name = "channel_name_1";
    private Notification notification;

    private static volatile  NotificationUtils mInsance;
    public static NotificationUtils getInsance(Context context){
        if (mInsance==null){
            mInsance = new NotificationUtils(context.getApplicationContext());
        }
        return mInsance;
    }

    private NotificationUtils(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Notification.Builder getChannelNotification(String title, String content,Class< ? extends Activity> zClass,boolean canCancle) {
        Notification.Builder builder = new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.icon_qq)
                .setOngoing(canCancle)
                .setAutoCancel(true);//点击之后自动删除
        if (zClass!=null){
            builder.setContentIntent(getPendIntent(zClass));
        }
        return builder;
    }

    private Notification.Builder getNotification_25(String title, String content,Class< ? extends Activity> zClass,boolean canCancle) {
        Notification.Builder builder = new Notification.Builder(this).setTicker("-")
                .setContentIntent(getPendIntent(zClass))
                .setSmallIcon(R.drawable.icon_qq).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_qq))
                .setContentText(title).setContentTitle(content)
                .setOngoing(canCancle)
                .setAutoCancel(true);
        if (zClass!=null){
            builder.setContentIntent(getPendIntent(zClass));
        }
        return builder;
    }

    private PendingIntent getPendIntent(Class< ? extends Activity> zClass) {
        Intent notificationIntenta = new Intent(this, zClass);
        PendingIntent contentIntent = PendingIntent.getActivities(this, 2002, new Intent[]{notificationIntenta}, PendingIntent.FLAG_UPDATE_CURRENT);
        return contentIntent;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(String title, String content,Class< ? extends Activity> zClass,boolean canCancle) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            this.notification = getChannelNotification
                    (title, content,zClass,canCancle).build();
            getManager().notify(1, notification);
        } else {
            this.notification = getNotification_25(title, content,zClass,canCancle).build();
            getManager().notify(1, notification);
        }
    }
    public void sendNotification(String title, String content,Class< ? extends Activity> zClass) {
       sendNotification(title, content, zClass,false);
    }


    public Notification getNotification() {
        return this.notification;
    }
}