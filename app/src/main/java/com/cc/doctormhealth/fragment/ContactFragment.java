package com.cc.doctormhealth.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.avoscloud.leanchatlib.event.MemberLetterEvent;
import com.avoscloud.leanchatlib.utils.Constants;
import com.cc.doctormhealth.leanchat.activity.ChatRoomActivity;
import com.cc.doctormhealth.leanchat.activity.ConversationGroupListActivity;
import com.cc.doctormhealth.leanchat.adapter.ContactsAdapter;
import com.cc.doctormhealth.leanchat.event.ContactItemClickEvent;
import com.cc.doctormhealth.leanchat.event.ContactItemLongClickEvent;
import com.cc.doctormhealth.leanchat.event.ContactRefreshEvent;
import com.cc.doctormhealth.leanchat.event.InvitationEvent;
import com.cc.doctormhealth.leanchat.friends.AddRequestManager;
import com.cc.doctormhealth.leanchat.friends.ContactAddFriendActivity;
import com.cc.doctormhealth.leanchat.friends.ContactNewFriendActivity;
import com.cc.doctormhealth.leanchat.friends.FriendsManager;
import com.cc.doctormhealth.leanchat.model.LeanchatUser;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.TextActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends BaseFragment {
    @Bind(R.id.activity_square_members_srl_list)
    protected SwipeRefreshLayout refreshLayout;

    @Bind(R.id.activity_square_members_rv_list)
    protected RecyclerView recyclerView;

    private View headerView;
    ImageView msgTipsView;

    private ContactsAdapter itemAdapter;
    LinearLayoutManager layoutManager;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        headerView = inflater.inflate(R.layout.contact_fragment_header_layout, container, false);
        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        itemAdapter = new ContactsAdapter();
        itemAdapter.setHeaderView(headerView);
        recyclerView.setAdapter(itemAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMembers(false);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initHeaderView();
        initHeader();
        refresh();
        EventBus.getDefault().register(this);
        getMembers(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNewRequestBadge();
    }

    private void initHeaderView() {
        msgTipsView = (ImageView) headerView.findViewById(R.id.iv_msg_tips);
        View newView = headerView.findViewById(R.id.layout_new);
        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ContactNewFriendActivity.class);
                ctx.startActivity(intent);
            }
        });

        View groupView = headerView.findViewById(R.id.layout_group);
        groupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ConversationGroupListActivity.class);
                ctx.startActivity(intent);
            }
        });
    }

    private void getMembers(final boolean isforce) {
        FriendsManager.fetchFriends(isforce, new FindCallback<LeanchatUser>() {
            @Override
            public void done(List<LeanchatUser> list, AVException e) {
                refreshLayout.setRefreshing(false);
                itemAdapter.setUserList(list);
                itemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initHeader() {
        headerLayout.showTitle(MyApplication.ctx.getString(R.string.contact));
        headerLayout.showRightImageButton(R.drawable.base_action_bar_add_bg_selector, new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String check = MyApplication.sharedPreferences.getString(com.cc.doctormhealth.constant.Constants.LOGIN_CHECK,
                        null);
                if ("2".equals(check)) {
                    Intent intent = new Intent(ctx, ContactAddFriendActivity.class);
                    ctx.startActivity(intent);
                } else if ("1".equals(check)) {
                    Toast.makeText(getActivity(), "正在认证资质,请稍等", Toast.LENGTH_LONG).show();
                } else if ("3".equals(check)) {
                    Toast.makeText(getActivity(), "资质认证失败,请重新认证", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getActivity(), TextActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void updateNewRequestBadge() {
        msgTipsView.setVisibility(
                AddRequestManager.getInstance().hasUnreadRequests() ? View.VISIBLE : View.GONE);
    }

    private void refresh() {
        AddRequestManager.getInstance().countUnreadRequests(new CountCallback() {
            @Override
            public void done(int i, AVException e) {
                updateNewRequestBadge();
            }
        });
    }

    public void showDeleteDialog(final String memberId) {
        new AlertDialog.Builder(ctx).setMessage(R.string.contact_deleteContact)
                .setPositiveButton(R.string.common_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog dialog1 = showSpinnerDialog();
                        LeanchatUser.getCurrentUser().removeFriend(memberId, new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                dialog1.dismiss();
                                if (filterException(e)) {
                                    getMembers(true);
                                }
                            }
                        });
                    }
                }).setNegativeButton(R.string.chat_common_cancel, null).show();
    }

    public void onEvent(ContactRefreshEvent event) {
        getMembers(true);
    }

    public void onEvent(InvitationEvent event) {
        AddRequestManager.getInstance().unreadRequestsIncrement();
        updateNewRequestBadge();
    }

    public void onEvent(ContactItemClickEvent event) {
        Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
        intent.putExtra(Constants.MEMBER_ID, event.memberId);
        startActivity(intent);
    }

    public void onEvent(ContactItemLongClickEvent event) {
        showDeleteDialog(event.memberId);
    }

    /**
     * 处理 LetterView 发送过来的 MemberLetterEvent
     * 会通过 MembersAdapter 获取应该要跳转到的位置，然后跳转
     */
    public void onEvent(MemberLetterEvent event) {
        Character targetChar = Character.toLowerCase(event.letter);
        if (itemAdapter.getIndexMap().containsKey(targetChar)) {
            int index = itemAdapter.getIndexMap().get(targetChar);
            if (index > 0 && index < itemAdapter.getItemCount()) {
                layoutManager.scrollToPositionWithOffset(index, 0);
            }
        }
    }
}















