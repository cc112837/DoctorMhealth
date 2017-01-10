package com.cc.doctormhealth.activity;
//关于
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.utils.PackageUtils;

public class AboutActivity extends Activity {
    private TextView tv_version, tv_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tv_service = (TextView) findViewById(R.id.tv_service);
        tv_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, ServicePriActivity.class);
                startActivity(intent);
            }
        });
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText("版本：" + PackageUtils.getPackageVersion(this));
        ImageView leftBtn = (ImageView) findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
