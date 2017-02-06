package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.doctormhealth.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookAidsActivity extends AppCompatActivity {

    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_bookcontent)
    TextView tvBookcontent;
    @Bind(R.id.tv_message)
    TextView tvMessage;
    @Bind(R.id.tv_start)
    TextView tvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_aids);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.leftBtn, R.id.tv_message, R.id.tv_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftBtn:
                finish();
                break;
            case R.id.tv_message:
                break;
            case R.id.tv_start:
                break;
        }
    }
}
