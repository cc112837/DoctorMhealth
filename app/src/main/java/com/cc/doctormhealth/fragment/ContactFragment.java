package com.cc.doctormhealth.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.avos.avoscloud.AVFriendship;
import com.avos.avoscloud.AVFriendshipQuery;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.callback.AVFriendshipCallback;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.Constants;
import com.cc.doctormhealth.LeanChat.activity.ChatRoomActivity;
import com.cc.doctormhealth.LeanChat.adapter.ContactsAdapter;
import com.cc.doctormhealth.LeanChat.event.ContactItemClickEvent;
import com.cc.doctormhealth.LeanChat.event.ContactItemLongClickEvent;
import com.cc.doctormhealth.LeanChat.event.ContactRefreshEvent;
import com.cc.doctormhealth.LeanChat.event.InvitationEvent;
import com.cc.doctormhealth.LeanChat.event.MemberLetterEvent;
import com.cc.doctormhealth.LeanChat.service.AddRequestManager;
import com.cc.doctormhealth.LeanChat.service.CacheService;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.NewFriendActivity;
import com.cc.doctormhealth.activity.SearchActivity;
import com.cc.doctormhealth.activity.TextActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    protected Context ctx;
    protected SwipeRefreshLayout refreshLayout;

    protected RecyclerView recyclerView;
    String check;
    private View headerView;
    ImageView msgTipsView,rightBtn;

    private ContactsAdapter itemAdapter;
    LinearLayoutManager layoutManager;

    private Handler handler = new Handler(Looper.getMainLooper());

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ctx = getActivity();
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        headerView = inflater.inflate(R.layout.contact_fragment_header_layout,
                container, false);
        rightBtn = (ImageView) view.findViewById(R.id.rightBtn);
        refreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.activity_square_members_srl_list);

        recyclerView = (RecyclerView) view
                .findViewById(R.id.activity_square_members_rv_list);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        check = MyApplication.sharedPreferences.getString(com.cc.doctormhealth.constant.Constants.LOGIN_CHECK,
                null);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("2".equals(check)) {
                    startActivity(new Intent(getActivity(), SearchActivity.class));
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
        itemAdapter = new ContactsAdapter();
        itemAdapter.setHeaderView(headerView);
        recyclerView.setAdapter(itemAdapter);

        refreshLayout
                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        myGetMenber();
                        // getMembers(true);
                    }
                });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initHeaderView();
        refresh();
        EventBus.getDefault().register(this);
        myGetMenber();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void myGetMenber() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    AVFriendshipQuery query = AVUser.friendshipQuery(AVUser
                            .getCurrentUser().getObjectId(), LeanchatUser.class);
                    query.include("followee");
                    query.include("follower");
                    query.getInBackground(new AVFriendshipCallback() {

                        @Override
                        public void done(AVFriendship friendship, AVException e) {
                            // TODO Auto-generated method stub
                            List<LeanchatUser> followers = friendship.getFollowers(); // 获取粉丝
                            List<LeanchatUser> followees = friendship.getFollowees(); // 获取关注列表
                            AVUser user = friendship.getUser(); // 获取用户对象本身
                            final List<LeanchatUser> users = new ArrayList<LeanchatUser>();
                            List<String> Ids = new ArrayList<String>();
                            for (LeanchatUser u : followers)
                                if (followees.contains(u)) {
                                    Ids.add(u.getObjectId());
                                    users.add(u);
                                }
                            CacheService.setFriendIds(Ids);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.setRefreshing(false);
                                    itemAdapter.setUserList(users);
                                    itemAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
                Intent intent = new Intent(ctx, NewFriendActivity.class);
                ctx.startActivity(intent);
            }
        });
        View groupView = headerView.findViewById(R.id.layout_group);
        groupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/1/10
//                Intent intent = new Intent(ctx, ConversationGroupListActivity.class);
//                ctx.startActivity(intent);
            }
        });
    }

    private void updateNewRequestBadge() {
        msgTipsView.setVisibility(AddRequestManager.getInstance()
                .hasUnreadRequests() ? View.VISIBLE : View.GONE);
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
        new AlertDialog.Builder(ctx)
                .setMessage(R.string.contact_deleteContact)
                .setPositiveButton(R.string.common_sure,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog dialog1 = showSpinnerDialog();
                                AVUser.getCurrentUser(LeanchatUser.class).removeFriend(
                                        memberId, new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                dialog1.dismiss();
                                                if (e == null) {
                                                    myGetMenber();
                                                    // getMembers(true);
                                                }
                                            }
                                        });
                            }
                        }).setNegativeButton(R.string.chat_common_cancel, null).show();
    }

    public static List<LeanchatUser> findFriends(boolean isforce)
            throws Exception {
        final List<LeanchatUser> friends = new ArrayList<LeanchatUser>();
        final AVException[] es = new AVException[1];
        final CountDownLatch latch = new CountDownLatch(1);
        LeanchatUser.getCurrentUser(LeanchatUser.class).findFriendsWithCachePolicy(
                isforce ? AVQuery.CachePolicy.NETWORK_ELSE_CACHE
                        : AVQuery.CachePolicy.CACHE_ELSE_NETWORK,
                new FindCallback<LeanchatUser>() {
                    @Override
                    public void done(List<LeanchatUser> avUsers, AVException e) {
                        if (e != null) {
                            es[0] = e;
                        } else {
                            friends.addAll(avUsers);
                        }
                        latch.countDown();
                    }
                });
        latch.await();
        if (es[0] != null) {
            throw es[0];
        } else {
            List<String> userIds = new ArrayList<String>();
            for (AVUser user : friends) {
                userIds.add(user.getObjectId());
            }
            CacheService.setFriendIds(userIds);
            CacheService.cacheUsers(userIds);
            List<LeanchatUser> newFriends = new ArrayList<LeanchatUser>();
            for (LeanchatUser user : friends) {
                newFriends.add(CacheService.lookupUser(user.getObjectId()));
            }
            return newFriends;
        }
    }

    public void onEvent(ContactRefreshEvent event) {
        // getMembers(true);
        myGetMenber();
    }

    public void onEvent(InvitationEvent event) {
        AddRequestManager.getInstance().unreadRequestsIncrement();
        updateNewRequestBadge();
    }

    public void onEvent(ContactItemClickEvent event) {

        final ChatManager chatManager = ChatManager.getInstance();
        chatManager.fetchConversationWithUserId(event.memberId,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conversation, AVIMException e) {
                        if (e != null) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                            intent.putExtra(Constants.CONVERSATION_ID,
                                    conversation.getConversationId());
                            startActivity(intent);
                        }
                    }
                });

        // Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
        // intent.putExtra(Constants.MEMBER_ID, event.memberId);
        // startActivity(intent);
    }

    public void onEvent(ContactItemLongClickEvent event) {
        showDeleteDialog(event.memberId);
    }

    /**
     * 处理 LetterView 发送过来的 MemberLetterEvent 会通过 MembersAdapter 获取应该要跳转到的位置，然后跳转
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

    protected ProgressDialog showSpinnerDialog() {
        // activity = modifyDialogContext(activity);
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(true);
        dialog.setMessage(getString(R.string.chat_utils_hardLoading));
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
        return dialog;
    }



}
