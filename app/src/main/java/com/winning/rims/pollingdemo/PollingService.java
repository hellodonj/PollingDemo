package com.winning.rims.pollingdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

/**
 * 描述: 智能提醒轮询服务
 * 作者|时间: djj on 2018/8/20 17:27
 * 博客地址: http://www.jianshu.com/u/dfbde65a03fc
 */

public class PollingService extends Service {

    private static final String TAG = "PollingService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "--------->onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "--------->onStartCommand: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("LongRunningService", "executed at " + new Date().toString());
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //此处是设置每隔5分钟启动一次
        int Minutes = 5 * 1000;//* 60
        //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间
        long triggerAtTime = SystemClock.elapsedRealtime() + Minutes;
        //此处设置开启AlarmReceiver这个Service
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "--------->onDestroy: ");
        //在Service结束后关闭AlarmManager
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.cancel(pi);

    }
}
