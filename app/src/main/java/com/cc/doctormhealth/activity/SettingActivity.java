package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.service.PushManager;

public class SettingActivity extends Activity implements View.OnClickListener{
    private ImageView leftBtn;
    private Button logout;
    private TextView tv_volume;
    private LinearLayout account,fankui,noti_news,cache;
    private ChatManager chatManager = ChatManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init() {
        leftBtn=(ImageView) findViewById(R.id.leftBtn);
        logout = (Button) findViewById(R.id.logout);
        tv_volume=(TextView)findViewById(R.id.tv_volume);
        account = (LinearLayout) findViewById(R.id.account);
        fankui = (LinearLayout) findViewById(R.id.fankui);
        noti_news = (LinearLayout) findViewById(R.id.noti_news);
        cache = (LinearLayout) findViewById(R.id.cache);

        noti_news.setOnClickListener(this);
        account.setOnClickListener(this);
        logout.setOnClickListener(this);
        fankui.setOnClickListener(this);
        cache.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.leftBtn:
                finish();
                break;
            case R.id.cache:
                Toast.makeText(SettingActivity.this, "清理完成", Toast.LENGTH_LONG).show();
                SystemClock.sleep(3000);
                tv_volume.setText("0M");
                break;
            case R.id.fankui:
                FeedbackAgent agent = new FeedbackAgent(SettingActivity.this);
                agent.startDefaultThreadActivity();
                break;
            case R.id.logout:
                chatManager.closeWithCallback(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                    }
                });
                PushManager.getInstance().unsubscribeCurrentUserChannel();
                AVUser.logOut();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.account:
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, ChangePwdActivity.class);
                startActivity(intent);
                break;
            case  R.id.noti_news:
                Intent intent1 = new Intent(SettingActivity.this, NotiNewsActivity.class);
                startActivity(intent1);
                break;
        }

    }
}

