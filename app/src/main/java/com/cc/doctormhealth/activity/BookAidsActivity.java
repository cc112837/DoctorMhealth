package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.activity.ChatRoomActivity;
import com.cc.doctormhealth.model.OederAids;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cc.doctormhealth.R.id.iv_head;

public class BookAidsActivity extends AppCompatActivity {

    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    @Bind(iv_head)
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
    private OederAids.DataEntity.AppointDataEntity entityAid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_aids);
        ButterKnife.bind(this);
        entityAid = (OederAids.DataEntity.AppointDataEntity) getIntent().getSerializableExtra("id");
        ImageLoader.getInstance().displayImage(entityAid.getUserImage(),ivHead, PhotoUtils.avatarImageOption);
        tvName.setText(entityAid.getName());
        tvSex.setText(entityAid.getSex());
        tvAge.setText(entityAid.getAge());
        tvContent.setText(entityAid.getCaseness());
        tvBookcontent.setText(entityAid.getIllness());
    }

    @OnClick({R.id.leftBtn, R.id.tv_message, R.id.tv_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftBtn:
                finish();
                break;
            case R.id.tv_message:
                ChatManager chatManager = ChatManager.getInstance();
                chatManager.fetchConversationWithUserId(entityAid.getUserId(),
                        new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation conversation, AVIMException e) {
                                if (e != null) {
                                    Toast.makeText(BookAidsActivity.this, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(BookAidsActivity.this,
                                            ChatRoomActivity.class);
                                    intent.putExtra(com.avoscloud.leanchatlib.utils.Constants.CONVERSATION_ID,
                                            conversation.getConversationId());
                                    startActivity(intent);}
                            }
                        });
                break;
            case R.id.tv_start:
                break;
        }
    }
}
