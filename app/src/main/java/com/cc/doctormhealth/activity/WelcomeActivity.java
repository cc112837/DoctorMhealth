package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String name = MyApplication.sharedPreferences.getString(Constants.LOGIN_ACCOUNT,
                        "");
                String check = MyApplication.sharedPreferences.getString(Constants.LOGIN_CHECK,
                        "");
                Intent intent;
                if ((name != "")&&(check.equals("1")) ) {
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    intent.putExtra("name",name);
                } else {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
        thread.start();
    }
}
