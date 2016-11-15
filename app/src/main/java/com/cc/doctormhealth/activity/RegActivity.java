package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.ForGetpass;
import com.cc.doctormhealth.model.UserInfo;
import com.cc.doctormhealth.utils.MyHttpUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegActivity extends BaseActivity implements View.OnClickListener{
    private EditText et_phone, et_code;
    private Button Message_btn, register_btn;
    private ImageView btn_back;
    private String flag;
    private String userPhone;
    private String phonecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        initSms();
        initView();
        initEvent();
    }
    private void initEvent() {
        register_btn.setOnClickListener(this);
        Message_btn.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        Message_btn = (Button) findViewById(R.id.Message_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        btn_back = (ImageView) findViewById(R.id.back);
    }

    private void initSms() {
        SMSSDK.initSDK(this, "17911ed3c824f", "93314de199c355e46dde099cc239882b");
    }
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 16:
                final ForGetpass forGetpass = (ForGetpass) msg.obj;
                if (forGetpass.getStatus().equals("1")) {
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("appkey", "17911ed3c824f");
                    params.addBodyParameter("phone", userPhone);
                    params.addBodyParameter("zone", "86");
                    params.addBodyParameter("code", phonecode);
                    new HttpUtils().send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, "https://webapi.sms.mob.com/sms/verify",
                            params, new RequestCallBack<Object>() {
                                @Override
                                public void onSuccess(ResponseInfo<Object> responseInfo) {
                                    try {
                                        JSONObject object = new JSONObject(responseInfo.result.toString());
                                        String s = object.getString("status");
                                        if ("200".equals(s)) {
                                            Intent intent = new Intent(RegActivity.this, CheckActivity.class);
                                            intent.putExtra("phone",userPhone+"");
                                            intent.putExtra("flag",flag);
                                            intent.putExtra("pass",forGetpass.getPassword()+"");
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(RegActivity.this, "验证失败", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onFailure(com.lidroid.xutils.exception.HttpException error, String msg) {

                                }

                            });
                } else {
                    Toast.makeText(RegActivity.this, forGetpass.getData(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
};
    @Override
    public void onClick(View v) {
        userPhone = et_phone.getText().toString();
        phonecode = et_code.getText().toString();
        switch (v.getId()) {
            case R.id.Message_btn:
                if (userPhone.length() != 11) {
                    Toast.makeText(this, "请输入11位有效手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.getSupportedCountries();
                    SMSSDK.getVerificationCode("86", userPhone);
                    Message_btn.setClickable(false);
                    Message_btn.setBackgroundColor(Color.GRAY);
                    Toast.makeText(RegActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            Message_btn.setBackgroundResource(R.drawable.btn_default_small_normal_disable);
                            Message_btn.setText(millisUntilFinished / 1000 + "秒");
                        }

                        @Override
                        public void onFinish() {
                            Message_btn.setClickable(true);
                            Message_btn.setBackgroundResource(R.drawable.btn_default_small_normal);
                            Message_btn.setText("重新发送");
                        }
                    }.start();
                    //进行获取验证码操作和倒计时1分钟操作
                    cn.smssdk.EventHandler eh = new EventHandler() {

                        @Override
                        public void afterEvent(int event, int result, Object data) {

                            if (result == SMSSDK.RESULT_COMPLETE) {
                                //回调完成
                                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                    //提交验证码成功
                                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                                    //返回支持发送验证码的国家列表
                                }
                            } else {
                                ((Throwable) data).printStackTrace();
                            }
                        }
                    };
                    SMSSDK.registerEventHandler(eh); //注册短信回调
                }
                break;
            case R.id.register_btn:
			if(userPhone.length() == 11&&phonecode.length()==4){
			if("reg".equals(flag)){
		
				 RequestParams params = new RequestParams();
                    params.addBodyParameter("appkey", "17911ed3c824f");
                    params.addBodyParameter("phone", userPhone);
                    params.addBodyParameter("zone", "86");
                    params.addBodyParameter("code", phonecode);
                    new HttpUtils().send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST, "https://webapi.sms.mob.com/sms/verify",
                            params, new RequestCallBack<Object>() {
                                @Override
                                public void onSuccess(ResponseInfo<Object> responseInfo) {
                                    try {
                                        JSONObject object = new JSONObject(responseInfo.result.toString());
                                        String s = object.getString("status");
                                        if ("200".equals(s)) {
                                            Intent intent = new Intent(RegActivity.this, CheckActivity.class);
                                            intent.putExtra("phone",userPhone+"");
                                            intent.putExtra("flag",flag);
                                            intent.putExtra("pass","");
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(RegActivity.this, "验证失败", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }

                                @Override
                                public void onFailure(com.lidroid.xutils.exception.HttpException error, String msg) {

                                }

                            });
			}
			else{
                String uri = Constants.SERVER_URL + "MhealthDoctorOldPasswordServlet";
                UserInfo userInfo = new UserInfo();
                userInfo.setPhone(userPhone + "");
                MyHttpUtils.handData(handler, 16, uri, userInfo);}
				}
				else{
					Toast.makeText(this, "请确保手机号码和验证码输入正确", Toast.LENGTH_SHORT).show();
				}
                break;
            case R.id.back:
                finish();
                break;
        }

    }
}
