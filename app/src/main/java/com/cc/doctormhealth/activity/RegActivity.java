package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.utils.Tool;

public class RegActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        //登录成功
        Tool.initToast(getApplicationContext(),
                getString(R.string.register_success));
        Intent intent = new Intent(RegActivity.this,
                LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
