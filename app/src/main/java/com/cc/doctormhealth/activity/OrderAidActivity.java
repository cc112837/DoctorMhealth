package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cc.doctormhealth.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderAidActivity extends AppCompatActivity {

    @Bind(R.id.rb_noworder)
    RadioButton rbNoworder;
    @Bind(R.id.rb_hisorder)
    RadioButton rbHisorder;
    @Bind(R.id.rg_title)
    RadioGroup rgTitle;
    @Bind(R.id.leftBtn)
    ImageView leftBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_aid);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        rgTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbNoworder.getId()) {

                } else if (checkedId == rbHisorder.getId()) {

                }
            }
        });
    }

    @OnClick({R.id.leftBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftBtn:
                finish();
                break;
        }
    }
}
