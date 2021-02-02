package com.example.assist.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


import com.example.assist.R;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Title: UPushUtils
 * <p>
 * Description:通知工具类
 * </p>
 * Author Zsy
 * Date 2019/3/7  11:18
 */
public class NotifyUtil {

    public final static String CRITICAL = "critical";
    public final static String IMPORTANCE = "importance";
    public final static String DEFAULT = "default";
    public final static String LOW = "low";
    public final static String MEDIA = "media";

    public static NotificationManager mNotificationManager;
    public static Context mContext;
    public static int notifyId = 1;
    public static NotificationCompat.Builder oldBuilder;
    private static Notification.Builder newBuilder;
    private static int mSmallIcon = R.mipmap.ic_launcher;

    public static void init(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        initBuild(context);
    }

    public static void initBuild(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createAllNotificationChannels(context);
            newBuilder = new Notification.Builder(context, DEFAULT)
                    .setSmallIcon(mSmallIcon)
                    .setTicker("ticker")
                    .setAutoCancel(true)
                    .setShowWhen(true);
        } else {
            oldBuilder = new NotificationCompat.Builder(context);
            oldBuilder.setTicker("ticker")
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                    .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
                    .setSmallIcon(mSmallIcon);
        }
    }

    public static Notification getIntentNotification(Context context, String title, String text) {
        if (title == null)
            title = "title";
        if (text == null)
            text = "text";
        if (newBuilder == null && oldBuilder == null)
            initBuild(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newBuilder.setContentTitle(title)
                    .setContentText(text);
            return newBuilder.build();
        } else {
            oldBuilder.setContentTitle(title)
                    .setContentText(text);
            return oldBuilder.build();
        }
    }

    public static void SimpleNotification(String title, String text) {
        if (title == null)
            title = "title";
        if (text == null)
            text = "text";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newBuilder.setContentTitle(title)
                    .setContentText(text);
            mNotificationManager.notify(++notifyId, newBuilder.build());
        } else {
            oldBuilder.setContentTitle(title)
                    .setContentText(text)
                    .setTicker("Ticker");//通知首次出现在通知栏，带上升动画效果的
            mNotificationManager.notify(++notifyId, oldBuilder.build());
        }

    }

    /**
     * 带意图的通知
     *
     * @param title
     * @param text
     * @param intent
     */
    public static void IntentNotification(String title, String text, Intent intent) {
        if (title == null)
            title = "title";
        if (text == null)
            text = "text";
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newBuilder.setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pendingIntent);
            mNotificationManager.notify(++notifyId, newBuilder.build());
        } else {
            oldBuilder.setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pendingIntent);
            mNotificationManager.notify(++notifyId, oldBuilder.build());
        }
    }

    /**
     * 固定的通知(不通过点击取消)
     *
     * @param title
     * @param text
     * @param intent
     */
    public static void FixedNotification(String title, String text, Intent intent) {
        if (title == null)
            title = "title";
        if (text == null)
            text = "text";
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newBuilder.setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true);
            mNotificationManager.notify(++notifyId, newBuilder.build());
        } else {
            Notification notification = oldBuilder.setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pendingIntent)
                    .build();
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            mNotificationManager.notify(++notifyId, notification);
        }
    }

    /**
     * 进度通知条
     *
     * @param title
     * @param progress
     */
    public static void ProgressNotification(String title, int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newBuilder.setContentTitle(title)
                    .setContentText(String.valueOf(progress) + "%")
                    .setOngoing(true)
                    .setProgress(100, progress, false);
            mNotificationManager.notify(100, newBuilder.build());
        } else {
            oldBuilder.setContentTitle(title)
                    .setContentText(String.valueOf(progress) + "%")
                    .setOngoing(true)
                    .setProgress(100, progress, false);
            mNotificationManager.notify(100, oldBuilder.build());
        }
    }

    /**
     * 别表通知
     *
     * @param title       标题
     * @param text
     * @param lineTitle   别表标题
     * @param summaryText 概要
     * @param lines       列表信息
     * @param intent      意图
     */
    public static void InboxNotification(String title, String text, String lineTitle, String summaryText, List<String> lines, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
            inboxStyle.setBigContentTitle(lineTitle);
            inboxStyle.setSummaryText(summaryText);
            for (int i = 0; i < lines.size(); i++) {
                inboxStyle.addLine(lines.get(i));
            }
            newBuilder.setContentTitle(title)
                    .setContentText(text)
                    .setStyle(inboxStyle)
                    .setContentIntent(pendingIntent);
            mNotificationManager.notify(++notifyId, newBuilder.build());
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle(lineTitle);
            inboxStyle.setSummaryText(summaryText);
            for (int i = 0; i < lines.size(); i++) {
                inboxStyle.addLine(lines.get(i));
            }
            oldBuilder.setContentTitle(title)
                    .setContentText(text)
                    .setStyle(inboxStyle)
                    .setContentIntent(pendingIntent);
            mNotificationManager.notify(++notifyId, oldBuilder.build());
        }
    }

    /**
     * 清除某指定id通知
     *
     * @param notifyId
     */
    public static void clearNotification(int notifyId) {
        if (mNotificationManager != null) {
            if (notifyId > 0) {
                mNotificationManager.cancel(notifyId);
            } else {
                mNotificationManager.cancelAll();
            }
        }
    }

    /**
     * 清除所有通知
     *
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void createAllNotificationChannels(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (nm == null) {
            return;
        }

        NotificationChannel mediaChannel = new NotificationChannel(
                MEDIA,
                "Media events",
                NotificationManager.IMPORTANCE_DEFAULT);

        mediaChannel.setSound(null, null);
        mediaChannel.setVibrationPattern(null);

        nm.createNotificationChannels(Arrays.asList(
                new NotificationChannel(
                        CRITICAL,
                        "Critical events",
                        NotificationManager.IMPORTANCE_HIGH),

                new NotificationChannel(
                        IMPORTANCE,
                        "Importance events",
                        NotificationManager.IMPORTANCE_DEFAULT),

                new NotificationChannel(
                        DEFAULT,
                        "Default events",
                        NotificationManager.IMPORTANCE_LOW),

                new NotificationChannel(
                        LOW,
                        "Low events",
                        NotificationManager.IMPORTANCE_MIN),

                //custom notification channel
                mediaChannel
        ));
    }

}
