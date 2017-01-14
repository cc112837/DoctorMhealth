package com.cc.doctormhealth.leanchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avoscloud.leanchatlib.activity.AVBaseActivity;
import com.avoscloud.leanchatlib.adapter.CommonListAdapter;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.ConversationManager;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.event.GroupItemClickEvent;
import com.cc.doctormhealth.leanchat.viewholder.GroupItemHolder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzw on 14-10-7.
 */
public class ConversationGroupListActivity extends AVBaseActivity {

    @Bind(R.id.activity_group_list_srl_view)
    protected RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    private CommonListAdapter<AVIMConversation> itemAdapter;

    private ConversationManager conversationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list_activity);
        ButterKnife.bind(this);
        initView();

        conversationManager = ConversationManager.getInstance();
        setTitle("群组");

        refreshGroupList();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        itemAdapter = new CommonListAdapter<>(GroupItemHolder.class);
        recyclerView.setAdapter(itemAdapter);
    }

    private void refreshGroupList() {
        conversationManager.findGroupConversationsIncludeMe(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> conversations, AVIMException e) {
                if (filterException(e)) {
                    itemAdapter.setDataList(conversations);
                    itemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void onEvent(GroupItemClickEvent event) {
        Intent intent = new Intent(ConversationGroupListActivity.this, ChatRoomActivity.class);
        intent.putExtra(Constants.CONVERSATION_ID, event.conversationId);
        startActivity(intent);
    }

    @OnClick(R.id.leftBtn)
    public void onClick() {
        finish();
    }
}
