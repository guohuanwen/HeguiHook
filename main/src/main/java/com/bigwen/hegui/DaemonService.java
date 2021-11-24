package com.bigwen.hegui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.bigwen.hegui.hook.HookUtil;
import com.bigwen.hegui.hook.InvokeInfo;
import com.bigwen.socket.server.ProtocolServer;
import com.bigwen.socket.SocketConst;

public class DaemonService extends Service {

    private static final String notificationId = "DaemonService";
    private static final String notificationName = "正在后台运行";
    private static final int ID = 124412;
    private BroadcastReceiver broadcastReceiver;
    private ProtocolServer protocolServer;

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(false);
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(ID, getNotification());

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                InvokeInfo info = (InvokeInfo)intent.getSerializableExtra(HookUtil.EXTRA_KEY);
                HookUtil.print(HookUtil.toJson(info));
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(HookUtil.BROADCAST_ACTION));
        protocolServer = new ProtocolServer(SocketConst.SERVER_PORT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    protocolServer.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (protocolServer != null) {
            protocolServer.exit();
        }
        unregisterReceiver(broadcastReceiver);
    }

    /**
     * 获取通知(Android8.0后需要)
     *
     * @return
     */
    private Notification getNotification() {
        Intent msgIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("DaemonService")
                .setContentIntent(pendingIntent)
                .setContentText("正在后台运行");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        return builder.build();
    }

    //client 可以通过Binder获取Service实例
    public class MyBinder extends Binder {
        public DaemonService getService() {
            return DaemonService.this;
        }
    }

    //通过binder实现调用者client与Service之间的通信
    private final MyBinder binder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
