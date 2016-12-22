package com.cc.doctormhealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.UserInfo;
import com.cc.doctormhealth.model.UserReg;
import com.cc.doctormhealth.utils.MyAndroidUtil;
import com.cc.doctormhealth.utils.MyHttpUtils;
import com.cc.doctormhealth.utils.Tool;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LoginActivity extends BaseActivity implements TextWatcher {

    Button loginBtn, regButton, forgetButton;
    TextView nameText, pwdText;
    private String name, pwd;
    ImageView headicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String appId = "cirdf9pJrnd6XpNW1Xn3OVf5-gzGzoHsz";
        String appKey = "eFwqv2nwhEDg9qdqzPUr3fga";
        headicon = (ImageView) findViewById(R.id.headicon);
        ImageLoader.getInstance().displayImage(MyApplication.sharedPreferences.getString(Constants.icon, null), headicon,
                PhotoUtils.avatarlogin);


        AVOSCloud.initialize(LoginActivity.this, appId, appKey);

        if (AVUser.getCurrentUser() != null) {
            name = AVUser.getCurrentUser().getUsername();
            finishLogin();
        }
        initTitle();
        forgetButton = (Button) findViewById(R.id.forgetButton);
        nameText = (TextView) findViewById(R.id.nameText);
        pwdText = (TextView) findViewById(R.id.pwdText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        regButton = (Button) findViewById(R.id.regButton);
        nameText.addTextChangedListener(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                pwd = pwdText.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Tool.initToast(LoginActivity.this, getString(R.string.register_name));
                } else if (TextUtils.isEmpty(pwd)) {
                    Tool.initToast(LoginActivity.this,
                            getString(R.string.register_password));
                } else {
                    loginAccount(name, pwd);
                }
            }
        });
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                intent.putExtra("flag", "for");
                startActivity(intent);
            }
        });
        regButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                intent.putExtra("flag", "reg");
                startActivity(intent);
            }
        });

        // 已登录过,自动登录
        name = MyApplication.sharedPreferences.getString(Constants.LOGIN_ACCOUNT,
                null);
        pwd = MyApplication.sharedPreferences.getString(Constants.LOGIN_PWD, null);
        if (name != null)
            nameText.setText(name);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 12:
                    UserReg userReg = (UserReg) msg.obj;
                    if (userReg.getStatus().equals("1")) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                        if (userReg.getMember() == 2||userReg.getMember()==1) {
                            MyAndroidUtil.editXmlByString(Constants.LOGIN_CHECK, userReg.getMember() + "");
                            finishLogin();
                        } else {
                            if(userReg.getMember()==3){
                                Toast.makeText(LoginActivity.this,"认证失败，请重新提交认证",Toast.LENGTH_LONG).show();
                            }
                            MyAndroidUtil.editXmlByString(Constants.LOGIN_CHECK, userReg.getMember() + "");
                            Intent intent = new Intent(LoginActivity.this, TextActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, userReg.getData(), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

    private void loginSerAccount(String name, String pwd) {
        String url = Constants.SERVER_URL + "MhealthDoctorLoginServlet";
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(name);
        userInfo.setPass(pwd);
        MyHttpUtils.handData(handler, 12, url, userInfo);
    }

    private void loginAccount(final String userName, final String password) {
        final ProgressDialog dialog = showSpinnerDialog();
        AVUser.logInInBackground("D" + userName, password,
                new LogInCallback<LeanchatUser>() {
                    @Override
                    public void done(LeanchatUser avUser, AVException e) {
                        dialog.dismiss();
                        if (e == null) {
                            AVUser aUser = AVUser.getCurrentUser();
                            aUser.get("property");
                            if (aUser.get("property").equals("doctor")) {
                                loginSerAccount(name, pwd);
                            } else {
                                AVUser.logOut();
                                Tool.initToast(LoginActivity.this,
                                        getResources().getString(R.string.login_error));
                            }
                        } else {
                            if (e.getCode() == 1) {
                               Toast.makeText(LoginActivity.this, "登录错误次数已超上限,请20分钟后重试",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }, LeanchatUser.class);
    }

    private void finishLogin() {
        // 第二个参数：登录标记 Tag
        AVIMClient currentClient = AVIMClient.getInstance("android", "Mobile");
        currentClient.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    // 与云端建立连接成功
                }
                else{
                }
            }
        });
        Constants.USER_NAME = name;
        ChatManager chatManager = ChatManager.getInstance();
        chatManager.setupManagerWithUserId(AVUser.getCurrentUser().getObjectId());
        chatManager.openClient(null);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        name = MyApplication.sharedPreferences.getString(Constants.LOGIN_ACCOUNT,
                null);
        if (name != null)
            nameText.setText(name);
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        MyAndroidUtil.editXmlByString(Constants.LOGIN_ACCOUNT, nameText.getText()
                .toString());
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        MyAndroidUtil.editXmlByString(Constants.LOGIN_ACCOUNT, nameText.getText()
                .toString());

    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        MyAndroidUtil.editXmlByString(Constants.LOGIN_ACCOUNT, nameText.getText()
                .toString());
    }

}
