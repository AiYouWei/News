package com.news;

import android.app.Notification;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.TYDaily.R;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import net.tsz.afinal.FinalHttp;

import org.androidx.frames.base.BaseApplication;
import org.androidx.frames.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Application
 *
 * @author slioe shu
 */
public class TYAppliaction extends BaseApplication {
    private static TYAppliaction instance;
    private TYSettings settings;
    private FinalHttp http;
    private PushAgent mPushAgent;

    @Override
    public void onCreate() {
        settings = new TYSettings(this);
        super.onCreate();
        instance = this;
        http = new FinalHttp();
        new PushMessageHandler();

        addPush();
    }

    private void addPush() {
        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 参考集成文档的1.6.3
             * http://dev.umeng.com/push/android/integration#1_6_3
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                super.handleMessage(context, uMessage);
                // TODO 友盟信息输出
                LogUtil.e(true, "TYAppliaction", "handleMessage ====");
            }

            /**
             * 参考集成文档的1.6.4
             * http://dev.umeng.com/push/android/integration#1_6_4
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView);
                        builder.setContentTitle(msg.title)
                                .setContentText(msg.text)
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);
                        Notification mNotification = builder.build();
                        //由于Android v4包的bug，在2.3及以下系统，Builder创建出来的Notification，并没有设置RemoteView，故需要添加此代码
                        mNotification.contentView = myNotificationView;
                        return mNotification;
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * 参考集成文档的1.6.2
         * http://dev.umeng.com/push/android/integration#1_6_2
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
            }
        };
        mPushAgent.setNotificationClickHandler(new PushMessageHandler());
    }

    /**
     * 获取当前Application对象
     *
     * @return 当前Application对象
     */
    public static TYAppliaction getInstance() {
        return instance;
    }

    public FinalHttp getHttp() {
        return http;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TYSettings getSettings() {
        return settings;
    }

    @Override
    public void setCommonParams() {
        Map<String, String> commomParams = new HashMap<>();
        commomParams.put("v", TYConstants.APP_VER);
        commomParams.put("app_id", TYConstants.DEVICE_OS);
        commomParams.put("model", android.os.Build.MODEL);
        commomParams.put("reqtime", String.valueOf(System.currentTimeMillis()));
        commomParams.put("version", String.valueOf(VERSION.RELEASE));
        getHttpRequester().setDefaultParams(commomParams);
    }

    // "v", TYConstants.APP_VER, "app_id", TYConstants.DEVICE_OS, "model", android.os.Build.MODEL, "reqtime", String.valueOf(System.currentTimeMillis()), "version", String.valueOf(VERSION.RELEASE)
}
