package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.utils.Tool;

public class ChangePwdActivity extends AppCompatActivity {

    EditText oldPwdView, pwdView, pwdView1;
    Button subBtn;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_change_pwd);
        oldPwdView = (EditText) findViewById(R.id.oldPwdView);
        pwdView = (EditText) findViewById(R.id.pwdView);
        pwdView1 = (EditText) findViewById(R.id.pwdView1);
        subBtn = (Button) findViewById(R.id.subBtn);
        btn_back=(ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (oldPwdView.getText().toString().equals("")) {
                    Tool.initToast(getApplicationContext(), "请输入旧密码");
                } else if (pwdView.getText().toString().equals("")) {
                    Tool.initToast(getApplicationContext(), "请输入新密码");
                } else if (pwdView1.getText().toString().equals("")) {
                    Tool.initToast(getApplicationContext(), "请确认新密码");
                } else if (!pwdView.getText().toString()
                        .equals(pwdView1.getText().toString())) {
                    Tool.initToast(getApplicationContext(), "两次密码不一致");
                } else if (!oldPwdView.getText().toString()
                        .equals(Constants.PWD)) {
                    Tool.initToast(getApplicationContext(), "原密码错误");
                } else {
                    AVUser user = AVUser.getCurrentUser();
                    user.updatePasswordInBackground(oldPwdView.getText()
                                    .toString(), pwdView.getText().toString(),
                            new UpdatePasswordCallback() {

                                @Override
                                public void done(AVException arg0) {
                                    if (arg0 == null) {
                                        Tool.initToast(getApplicationContext(),
                                                "修改密码成功");
                                        finish();
                                    } else
                                        Tool.initToast(getApplicationContext(),
                                                "修改密码失败");
                                }
                            });
                }
            }
        });
    }
}
