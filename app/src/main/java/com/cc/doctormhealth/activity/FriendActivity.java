package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.Tool;

public class FriendActivity extends AppCompatActivity {
    Button operBtn;
    TextView nameView, sexView, signView, nickNameView, phoneView, emailView;
    ImageView headView,leftBtn;
    private String username;

    private User friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        leftBtn=(ImageView) findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        username = getIntent().getStringExtra("username");
        // TODO: 2016/5/21(username木有值)


        if (username.equals(Constants.USER_NAME)) {
            operBtn.setVisibility(View.GONE);
        }
//        isFriend();
    }

    public void isFriend() {
        operBtn.setText("添加到通讯录");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.operBtn:
                if (operBtn.getText().equals("添加到通讯录")) {
                    Tool.initToast(getApplicationContext(), "添加成功，等待通过验证");
                    MyApplication.getInstance().sendBroadcast(
                            new Intent("friendChange"));
                    isFriend();
                } else if (operBtn.getText().equals("移出通讯录")) {
                    Tool.initToast(getApplicationContext(), "移除成功");
                    MyApplication.getInstance().sendBroadcast(
                            new Intent("friendChange"));
                    MyApplication.getInstance().sendBroadcast(
                            new Intent("ChatNewMsg"));
                    operBtn.setText("添加到通讯录");
                    finish();
                }
                break;

            default:
                break;
        }

    }
}

