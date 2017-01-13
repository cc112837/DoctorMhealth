package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.cc.doctormhealth.leanchat.model.LeanchatUser;
import com.cc.doctormhealth.leanchat.service.PushManager;
import com.cc.doctormhealth.leanchat.util.Utils;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.Result;
import com.cc.doctormhealth.model.UserInfo;
import com.cc.doctormhealth.model.UserReg;
import com.cc.doctormhealth.utils.MyAndroidUtil;
import com.cc.doctormhealth.utils.MyHttpUtils;
import com.cc.doctormhealth.utils.Tool;

import static com.cc.doctormhealth.leanchat.util.Utils.filterException;


public class CheckActivity extends Activity {
    private EditText register_password, register_password_again;
    private Button confirm_btn;
    private ImageView btn_back;
    private String phone, pass;
    private String flag;
    private String findpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        flag = intent.getStringExtra("flag");
        findpass = intent.getStringExtra("pass");
        init();

    }

    private void init() {
        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        btn_back = (ImageView) findViewById(R.id.back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register_password = (EditText) findViewById(R.id.register_password);
        register_password_again = (EditText) findViewById(R.id.register_password_again);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = register_password.getText().toString();
                String pass_again = register_password_again.getText().toString();
                if (pass.length() < 6 || pass_again.length() < 6) {
                    Toast.makeText(CheckActivity.this, "请输入6位或者6位以上密码", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(pass_again)) {
                    Toast.makeText(CheckActivity.this, "输入密码不一致，请重新确认", Toast.LENGTH_SHORT).show();
                } else {
                    if ("reg".equals(flag)) {
                        createAccount(phone, pass);
                    } else {
                        LeanchatUser.logInInBackground("D" + phone, findpass,
                                new LogInCallback<LeanchatUser>() {
                                    @Override
                                    public void done(LeanchatUser avUser, AVException e) {
                                        if (e == null) {
                                            final AVUser aUser = AVUser.getCurrentUser();
                                            aUser.get("property");
                                            if (aUser.get("property").equals("doctor")) {
                                                // 第二个参数：登录标记 Tag
                                                ChatManager.getInstance().openClient(CheckActivity.this, LeanchatUser.getCurrentUserId(), new AVIMClientCallback() {
                                                    @Override
                                                    public void done(AVIMClient avimClient, AVIMException e) {
                                                        if (filterException(e)) {
                                                            aUser.updatePasswordInBackground(findpass, pass,
                                                                    new UpdatePasswordCallback() {

                                                                        @Override
                                                                        public void done(AVException arg0) {
                                                                            if (arg0 == null) {
                                                                                String utr = Constants.SERVER_URL + "MhealthDoctorPasswordServlet";
                                                                                UserInfo user = new UserInfo();
                                                                                user.setPhone(phone + "");
                                                                                user.setPass(pass + "");
                                                                                MyHttpUtils.handData(handler, 17, utr, user);
                                                                            } else
                                                                                Tool.initToast(getApplicationContext(),
                                                                                        "设置密码失败");
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });

                                            } else {
                                                ChatManager chatManager = ChatManager.getInstance();
                                                chatManager.closeWithCallback(new AVIMClientCallback() {
                                                    @Override
                                                    public void done(AVIMClient avimClient, AVIMException e) {
                                                    }
                                                });
                                                PushManager.getInstance().unsubscribeCurrentUserChannel();
                                                AVUser.logOut();
                                                Tool.initToast(CheckActivity.this,
                                                        getResources().getString(R.string.login_error));
                                            }
                                        } else
                                            Tool.initToast(CheckActivity.this, "设置密码失败");
                                    }
                                }, LeanchatUser.class);
                    }

                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    UserReg userReg = (UserReg) msg.obj;
                    if (userReg.getStatus().equals("1")) {
                        MyAndroidUtil.editXmlByString(Constants.LOGIN_ACCOUNT,
                                userReg.getMobile());
                        Intent intent = new Intent(CheckActivity.this,
                                LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Utils.toast("注册成功，请登录");
                        finish();
                    } else {
                        Utils.toast(MyApplication.getInstance().getString(
                                        R.string.registerFailed)
                        );
                    }
                    break;

                case 17:
                    Result result = (Result) msg.obj;
                    if (result.getStatus().equals("1")) {
                        Toast.makeText(CheckActivity.this, "密码设置成功，请重新登录", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(CheckActivity.this, "密码设置失败，请重新设置", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

    private void creaSerAccount(String userName, String passWord) {
        String url = Constants.SERVER_URL + "MhealthDoctorRegisterServlet";
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(userName);
        userInfo.setPass(passWord);
        MyHttpUtils.handData(handler, 11, url, userInfo);
    }

    private void createAccount(final String userName, final String passWord) {
        LeanchatUser.signUpByNameAndPwdAndProperty("D" + userName, passWord, "doctor", new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    Utils.toast(MyApplication.getInstance().getString(
                            R.string.registerFailed)
                            + e.getMessage());
                } else {
                    creaSerAccount(phone, pass);
                }
            }
        });

    }
}
