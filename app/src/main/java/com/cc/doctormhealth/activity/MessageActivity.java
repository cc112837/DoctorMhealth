package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.cc.doctormhealth.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends AppCompatActivity {
    @Bind(R.id.leftBtn)
    ImageView leftBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.leftBtn)
    public void onClick() {
        finish();
    }
}
