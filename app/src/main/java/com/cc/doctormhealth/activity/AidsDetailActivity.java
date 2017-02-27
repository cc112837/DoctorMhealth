package com.cc.doctormhealth.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.activity.ChatRoomActivity;
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
    AidManager.DataEntity content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aids_detail);
        ButterKnife.bind(this);
        content = (AidManager.DataEntity) getIntent().getSerializableExtra("content");
        tvName.setText(content.getName());
        tvAge.setText(content.getAge());
        ImageLoader.getInstance().displayImage(content.getUserImage(), ivHead, PhotoUtils.avatarImageOption);
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
                if ("0".equals(content.getCheckCase())) {
                    Intent intent = new Intent(AidsDetailActivity.this, BookManagerActivity.class);
                    intent.putExtra("id", content.getAppointId());
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(AidsDetailActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("暂无病历可以查询！")//设置显示的内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    dialog.dismiss();
                                }

                            }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            dialog.dismiss();
                        }
                    }).show();//在按键响应事件中显示此对话框
                }
                break;
            case R.id.re_ask:
                ChatManager chatManager = ChatManager.getInstance();
                chatManager.fetchConversationWithUserId(content.getUserId(),
                        new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation conversation, AVIMException e) {
                                if (e != null) {
                                    Toast.makeText(AidsDetailActivity.this, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(AidsDetailActivity.this,
                                            ChatRoomActivity.class);
                                    intent.putExtra(com.avoscloud.leanchatlib.utils.Constants.CONVERSATION_ID,
                                            conversation.getConversationId());
                                    startActivity(intent);
                                }
                            }
                        });
                break;
            case R.id.re_order:
                Intent intent1 = new Intent(AidsDetailActivity.this, HistoryOrderActivity.class);
                intent1.putExtra("id", content.getAppointId());
                startActivity(intent1);
                break;
        }
    }
}
