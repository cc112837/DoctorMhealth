package com.cc.doctormhealth;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.LeanChat.model.AddRequest;
import com.cc.doctormhealth.LeanChat.model.UpdateInfo;
import com.cc.doctormhealth.LeanChat.service.ConversationManager;
import com.cc.doctormhealth.LeanChat.service.PushManager;
import com.cc.doctormhealth.LeanChat.util.Logger;
import com.cc.doctormhealth.LeanChat.util.Utils;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.constant.ImgConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.Thread.UncaughtExceptionHandler;

public class MyApplication extends Application implements
        UncaughtExceptionHandler {
    private static MyApplication instance;
    public static SharedPreferences sharedPreferences;
    public static boolean debug = true;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        ImgConfig.initImageLoader();
        Utils.fixAsyncTaskBug();
        String appId = "cirdf9pJrnd6XpNW1Xn3OVf5-gzGzoHsz";
        String appKey = "eFwqv2nwhEDg9qdqzPUr3fga";


        AVUser.alwaysUseSubUserClass(LeanchatUser.class);
        AVOSCloud.initialize(this, appId, appKey);
        AVObject.registerSubclass(AddRequest.class);
        AVObject.registerSubclass(UpdateInfo.class);
        // 节省流量
        AVOSCloud.setLastModifyEnabled(true);

        PushManager.getInstance().init(instance);
        AVOSCloud.setDebugLogEnabled(debug);
        AVAnalytics.enableCrashReport(this, !debug);
        initImageLoader(instance);
        if (MyApplication.debug) {
            openStrictMode();
        }

        initChatManager();

        if (MyApplication.debug) {
            Logger.level = Logger.VERBOSE;
        } else {
            Logger.level = Logger.NONE;
        }

    }

    private void initChatManager() {
        final ChatManager chatManager = ChatManager.getInstance();
        chatManager.init(this);
        if (LeanchatUser.getCurrentUser() != null) {
            chatManager.setupManagerWithUserId(LeanchatUser.getCurrentUser()
                    .getObjectId());
        }
        chatManager.setConversationEventHandler(ConversationManager
                .getEventHandler());
        ChatManager.setDebugEnabled(MyApplication.debug);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void openStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                        // .detectAll()
                        // for
                        // all
                        // detectable
                        // problems
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog()
                        // .penaltyDeath()
                .build());
    }

    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                        // .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
}
