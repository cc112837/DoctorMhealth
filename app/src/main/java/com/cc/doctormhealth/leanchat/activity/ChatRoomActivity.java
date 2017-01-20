package com.cc.doctormhealth.leanchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.activity.AVBaseActivity;
import com.avoscloud.leanchatlib.activity.ChatFragment;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.ConversationHelper;
import com.avoscloud.leanchatlib.model.ConversationType;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.cc.doctormhealth.R;

/**
 * Created by cc112837@163.com on 16/5/18.
 */
public class ChatRoomActivity extends AVBaseActivity {
    public static final int QUIT_GROUP_REQUEST = 200;
    public ImageView leftBtn,people;
    public TextView titleViewOfChatRoom;
    protected ChatFragment chatFragment;
    protected AVIMConversation conversation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentById(com.avoscloud.leanchatlib.R.id.fragment_chat);
        titleViewOfChatRoom=(TextView) findViewById(R.id.titleViewOfChatRoom);
        leftBtn=(ImageView) findViewById(R.id.leftBtn);
        people=(ImageView) findViewById(R.id.people);
        initByIntent(getIntent());
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != conversation) {
                    Intent intent = new Intent(ChatRoomActivity.this, ConversationDetailActivity.class);
                    intent.putExtra(Constants.CONVERSATION_ID, conversation.getConversationId());
                    startActivityForResult(intent, QUIT_GROUP_REQUEST);
                }
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initByIntent(intent);
    }

    private void initByIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (null != extras) {
            if (extras.containsKey(Constants.MEMBER_ID)) {
                getConversation(extras.getString(Constants.MEMBER_ID));
            } else if (extras.containsKey(Constants.CONVERSATION_ID)) {
                String conversationId = extras.getString(Constants.CONVERSATION_ID);
                updateConversation(AVIMClient.getInstance(ChatManager.getInstance().getSelfId()).getConversation(conversationId));
            } else {
            }
        }
    }
    protected void updateConversation(AVIMConversation conversation) {
        if (null != conversation) {
            this.conversation = conversation;
            chatFragment.setConversation(conversation);
            chatFragment.showUserName(ConversationHelper.typeOfConversation(conversation) != ConversationType.Single || ConversationHelper.typeOfConversation(conversation) != ConversationType.Doctor);
            titleViewOfChatRoom.setText(ConversationHelper.titleOfConversation(conversation));
        }
    }

    /**
     * 获取 conversation，为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
     * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
     */
    private void getConversation(final String memberId) {
        ChatManager.getInstance().createSingleConversation(memberId, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (filterException(e)) {
                    ChatManager.getInstance().getRoomsTable().insertRoom(avimConversation.getConversationId());
                    updateConversation(avimConversation);
                }
            }
        });
    }
    @Override
    protected void onResume() {
        NotificationUtils.cancelNotification(this);
        super.onResume();
    }

}
