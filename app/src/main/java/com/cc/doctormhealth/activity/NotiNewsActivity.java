package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.utils.MyAndroidUtil;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 新消息通知页面
*/
public class NotiNewsActivity extends AppCompatActivity {
    ImageView btn_back;

    ToggleButton switch1, switch2;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_noti_news);
        btn_back=(ImageView) findViewById(R.id.leftBtn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch1 = (ToggleButton) findViewById(R.id.switch1);
        switch2 = (ToggleButton) findViewById(R.id.switch2);
        if(MyApplication.sharedPreferences.getBoolean("isSound", true))
            switch1.setChecked(true);
        else if(MyApplication.sharedPreferences.getBoolean("isSound", false))
            switch1.setChecked(false);
        if(MyApplication.sharedPreferences.getBoolean("isShake", true))
            switch2.setChecked(true);
        else if(MyApplication.sharedPreferences.getBoolean("isShake", false))
            switch2.setChecked(false);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                MyAndroidUtil.editXml("isSound", isChecked);
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                MyAndroidUtil.editXml("isShake", isChecked);
            }
        });

    }
}
