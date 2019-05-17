package com.winning.rims.pollingdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 描述: 智能提醒定时广播
 * 作者|时间: djj on 2018/8/20 17:26
 * 博客地址: http://www.jianshu.com/u/dfbde65a03fc
 *
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //获取PendingIntent
        Intent mainIntent = new Intent(context, SplashActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //获取NotificationManager实例
        NotificationManager mManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builder并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                //默认通知效果
                .setDefaults(Notification.DEFAULT_VIBRATE)
                //设置通知标题
                .setContentTitle("新消息提醒!")
                //设置通知内容
                .setContentText("提醒内容：XXXXXXX")
                //设置通知时间，默认为系统发出通知的时间，通常不用设置
                .setWhen(System.currentTimeMillis())
                .setContentIntent(mainPendingIntent);
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        mManager.notify(1, builder.build());
        //再次开启LongRunningService这个服务，从而可以
        Intent i = new Intent(context, PollingService.class);
        context.startService(i);

    }
}
