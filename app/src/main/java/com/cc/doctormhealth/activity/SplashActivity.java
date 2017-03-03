package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.avos.avoscloud.AVAnalytics;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.utils.PackageUtils;


/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 导航页
 */
public class SplashActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sp = getSharedPreferences("version", MODE_PRIVATE);

        //相当于旧版本
        final String version = sp.getString("version", null);

        //相当于新版本
        final String newVersion = PackageUtils.getPackageVersion(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //说明登录过，不需要进入导航页，直接进入主界面
                //当新版本与旧版本一致时直接跳转进入主界面
                if (newVersion.equals(version)) {

                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {//需要进入导航页
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        thread.start();
    }

    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }
}
