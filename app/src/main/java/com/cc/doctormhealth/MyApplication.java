package com.cc.doctormhealth;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.ThirdPartUserUtils;
import com.cc.doctormhealth.leanchat.friends.AddRequest;
import com.cc.doctormhealth.leanchat.model.LeanchatUser;
import com.cc.doctormhealth.leanchat.model.UpdateInfo;
import com.cc.doctormhealth.leanchat.service.PushManager;
import com.cc.doctormhealth.leanchat.util.LeanchatUserProvider;
import com.cc.doctormhealth.leanchat.util.Utils;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.constant.ImgConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.Thread.UncaughtExceptionHandler;

public class MyApplication extends Application implements
        UncaughtExceptionHandler {
    public static MyApplication ctx;
    public static SharedPreferences sharedPreferences;
    public static boolean debug = true;

    public static MyApplication getInstance() {
        return ctx;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        ImgConfig.initImageLoader();
        Utils.fixAsyncTaskBug();
        String appId = "cirdf9pJrnd6XpNW1Xn3OVf5-gzGzoHsz";
        String appKey = "eFwqv2nwhEDg9qdqzPUr3fga";
        LeanchatUser.alwaysUseSubUserClass(LeanchatUser.class);
        AVObject.registerSubclass(AddRequest.class);
        AVObject.registerSubclass(UpdateInfo.class);

        AVOSCloud.initialize(this, appId, appKey);

        // 节省流量
        AVOSCloud.setLastModifyEnabled(true);

        PushManager.getInstance().init(ctx);
        AVOSCloud.setDebugLogEnabled(debug);
//        AVAnalytics.enableCrashReport(this, !debug);
        initImageLoader(ctx);
        if (MyApplication.debug) {
            openStrictMode();
        }

        ThirdPartUserUtils.setThirdPartUserProvider(new LeanchatUserProvider());
        ChatManager.getInstance().init(this);
        ChatManager.getInstance().setDebugEnabled(MyApplication.debug);
    }

    public void openStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                //.penaltyDeath()
                .build());
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
