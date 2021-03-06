package com.kogitune.devinfonotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by takam on 2014/09/08.
 */
public class DevInfoNotification {
    private final Context mContext;
    private final HardwareInfo mHardwareInfo;
    private final NotificationManager mNotificationManager;

    private static final String SHOW_NOTIFICATION = "SHOW_NOTIFICATION";
    private static final int NOTIFICATION_ID = 1;
    private final SharedPreferences mPreferences;

    public DevInfoNotification(Context context, HardwareInfo info) {
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mHardwareInfo = info;
    }

    public boolean isNotificationEnabled(){
        return mPreferences.getBoolean(DevInfoNotification.SHOW_NOTIFICATION,true);
    }
    public boolean setNotificationEnabled(boolean enabled) {
        return mPreferences.edit().putBoolean(DevInfoNotification.SHOW_NOTIFICATION, enabled).commit();
    }

    public void settingByPref (){
        if(isNotificationEnabled()){
            show();
		} else {
            cancel();
        }
    }

    public void show() {
        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        contentView.setTextViewText(R.id.text_model, mHardwareInfo.getModel());
        contentView.setTextViewText(R.id.text_dpi, mHardwareInfo.getDpi(mContext));
        contentView.setTextViewText(R.id.text_os, mHardwareInfo.getOs());
        contentView.setTextViewText(R.id.text_size, mHardwareInfo.getScreenSize(mContext));
        Notification notification = new NotificationCompat.Builder(mContext).setSmallIcon(R.drawable.ic_launcher).setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(mContext, 0, new Intent(mContext,MainActivity.class), 0)).build();
        notification.contentView = contentView;
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }
    public void cancel() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
