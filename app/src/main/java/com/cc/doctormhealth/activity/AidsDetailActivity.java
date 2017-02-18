package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.model.AidManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AidsDetailActivity extends AppCompatActivity {

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
    @Bind(R.id.re_book)
    RelativeLayout reBook;
    @Bind(R.id.re_ask)
    RelativeLayout reAsk;
    @Bind(R.id.re_order)
    RelativeLayout reOrder;
    AidManager.DataEntity  content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aids_detail);
        ButterKnife.bind(this);
         content = (AidManager.DataEntity)getIntent().getSerializableExtra("content");
        tvName.setText(content.getName());
        tvAge.setText(content.getAge());
        ImageLoader.getInstance().displayImage(content.getUserImage(),ivHead, PhotoUtils.avatarImageOption);
        tvSex.setText(content.getSex());
        tvContent.setText(content.getIllness());
    }

    @OnClick({R.id.leftBtn, R.id.re_book, R.id.re_ask, R.id.re_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftBtn:
                finish();
                break;
            case R.id.re_book:
                Intent intent=new Intent(AidsDetailActivity.this,BookManagerActivity.class);
                intent.putExtra("id",content.getAppointId());
                startActivity(intent);
                break;
            case R.id.re_ask:
                Intent intent2=new Intent(AidsDetailActivity.this,MessageActivity.class);
                startActivity(intent2);
                break;
            case R.id.re_order:
                Intent intent1=new Intent(AidsDetailActivity.this,OrderAidActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
