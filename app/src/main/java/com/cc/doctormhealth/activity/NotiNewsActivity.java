package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.utils.MyAndroidUtil;

public class NotiNewsActivity extends AppCompatActivity {

    ToggleButton switch1, switch2;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_noti_news);
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
