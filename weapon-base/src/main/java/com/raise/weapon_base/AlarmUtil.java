package com.raise.weapon_base;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;


/**
 * 1.设置单次响起闹钟
 * 2.设置重复响起闹钟
 * 3.取消已设置的闹钟
 */
public class AlarmUtil {

    private static final String PROXY_BROADCAST_PREFIX = "proxy.";
    private static final BroadcastReceiver proxyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null
                    && action.startsWith(PROXY_BROADCAST_PREFIX)) {
                String realAction = action.replace(PROXY_BROADCAST_PREFIX, "");
                long intervalTime = intent.getLongExtra("intervalTime", -1);
                int id = intent.getIntExtra("id", -1);

                Intent intent2 = new Intent(realAction);
                intent2.setPackage(AppInfoUtil.getPackageName());
                context.sendBroadcast(intent2);
                setRepeatAlarm(System.currentTimeMillis() + intervalTime, intervalTime, id, realAction);
            }
        }
    };

    /**
     * 设置重复闹钟
     *
     * @param startTime    闹钟第一次响起时间
     * @param intervalTime 闹钟之间的间隔时间
     * @param id           闹钟id,用于取消闹钟
     * @param action       闹钟响起时，通知的广播action
     */
    public static void setRepeatAlarm(long startTime, long intervalTime, int id, String action) {
        setRepeatAlarm(startTime, intervalTime, id, action, null);
    }

    public static void setRepeatAlarm(long startTime, long intervalTime, int id, String action, Bundle bundle) {
        String proxyAction = PROXY_BROADCAST_PREFIX + action;
        Intent intent = new Intent(proxyAction);
        intent.setPackage(AppInfoUtil.getPackageName());
        intent.putExtra("intervalTime", intervalTime);
        intent.putExtra("id", id);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ContextVal.getContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        registerProxyReceiver(proxyAction);
        setAlarm(startTime, pendingIntent);
    }

    public static void setOnceAlarm(long startTime, int id, String action) {
        setOnceAlarm(startTime, id, action, null);
    }

    public static void setOnceAlarm(long startTime, int id, String action, Bundle bundle) {
        Intent intent = new Intent(action);
        intent.putExtra("id", id);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        //安卓8.0以上,自定义广播需要指定包名
        intent.setPackage(AppInfoUtil.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ContextVal.getContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        setAlarm(startTime, pendingIntent);
    }

    public static void stopAlarm(int id, String action) {
        AlarmManager am = (AlarmManager) ContextVal.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("id", id);
        PendingIntent broadcast = PendingIntent.getBroadcast(ContextVal.getContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.cancel(broadcast);
    }

    public static void stopAlarm(PendingIntent pendingIntent) {
        AlarmManager am = (AlarmManager) ContextVal.getContext().getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
    }

    private static void registerProxyReceiver(String proxyAction) {
        ContextVal.getContext().registerReceiver(proxyReceiver, new IntentFilter(proxyAction));
    }

    private static void setAlarm(long startTime, PendingIntent pendingIntent) {
        AlarmManager am = (AlarmManager) ContextVal.getContext().getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= 23) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);
        }
    }
}
