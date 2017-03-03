package com.cc.doctormhealth.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.SaveCallback;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.avoscloud.leanchatlib.view.ViewHolder;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.activity.BaseActivityOfLeanCloud;
import com.cc.doctormhealth.leanchat.adapter.BaseListAdapter;
import com.cc.doctormhealth.leanchat.event.ContactRefreshEvent;
import com.cc.doctormhealth.leanchat.model.AddRequest;
import com.cc.doctormhealth.leanchat.service.AddRequestManager;
import com.cc.doctormhealth.leanchat.service.ConversationManager;
import com.cc.doctormhealth.leanchat.service.PreferenceMap;
import com.cc.doctormhealth.leanchat.util.RefreshTask;
import com.cc.doctormhealth.leanchat.util.Refreshable;
import com.cc.doctormhealth.leanchat.view.BaseListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 添加的新朋友介绍
*/
public class NewFriendActivity extends BaseActivityOfLeanCloud implements
        Refreshable {
    BaseListView<AddRequest> listView;
    private NewFriendListAdapter adapter;
    ImageView left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        initView();
        refresh();
    }

    public void refresh() {
        listView.onRefresh();
    }

    private void initView() {
        listView = (BaseListView) findViewById(R.id.newfriendList);
        left = (ImageView) findViewById(R.id.leftBtn);
        left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new NewFriendListAdapter(this);
        adapter.setListener(new NewFriendListAdapter.Listener() {
            @Override
            public void onAgreeAddRequest(final AddRequest addRequest) {
                final ProgressDialog dialog = showSpinnerDialog();

                AVUser.getCurrentUser().followInBackground(addRequest.getToUser().getObjectId(), new FollowCallback() {
                    @Override
                    public void done(AVObject object, AVException e) {
//					            if (e == null) {
//					            } else if (e.getCode() == AVException.DUPLICATE_VALUE) {
//					            }
                    }
                });
                AddRequestManager.getInstance().agreeAddRequest(addRequest,
                        new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                dialog.dismiss();
                                if (e == null) {
                                    if (addRequest.getFromUser() != null) {
                                        ConversationManager.getInstance()
                                                .sendWelcomeMessage(
                                                        addRequest
                                                                .getFromUser()
                                                                .getObjectId());
                                    }
                                    refresh();
                                    ContactRefreshEvent event = new ContactRefreshEvent();
                                    EventBus.getDefault().post(event);
                                }
                            }
                        });
            }
        });

        listView.init(new BaseListView.DataFactory<AddRequest>() {
            @Override
            public List<AddRequest> getDatasInBackground(int skip, int limit,
                                                         List<AddRequest> currentDatas) throws Exception {
                List<AddRequest> addRequests = AddRequestManager.getInstance()
                        .findAddRequests(skip, limit);
                AddRequestManager.getInstance()
                        .markAddRequestsRead(addRequests);
                List<AddRequest> filters = new ArrayList<AddRequest>();
                for (AddRequest addRequest : addRequests) {
                    if (addRequest.getFromUser() != null) {
                        filters.add(addRequest);
                    }
                }
                addRequests = filters;
                PreferenceMap preferenceMap = new PreferenceMap(ctx,
                        LeanchatUser.getCurrentUser().getObjectId());
                preferenceMap.setAddRequestN(addRequests.size());
                return addRequests;
            }
        }, adapter);

        listView.setItemListener(new BaseListView.ItemListener<AddRequest>() {
            @Override
            public void onItemLongPressed(final AddRequest item) {
                new AlertDialog.Builder(ctx)
                        .setMessage(R.string.contact_deleteFriendRequest)
                        .setPositiveButton(R.string.common_sure,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        deleteAddRequest(item);
                                    }
                                })
                        .setNegativeButton(R.string.chat_common_cancel, null)
                        .show();
            }
        });
    }

    private void deleteAddRequest(final AddRequest addRequest) {
        new RefreshTask(ctx, this) {
            @Override
            protected void doInBack() throws Exception {
                addRequest.delete();
                refresh();
            }
        }.execute();
    }

    public static class NewFriendListAdapter extends
            BaseListAdapter<AddRequest> {
        private Listener listener;

        public NewFriendListAdapter(Context ctx) {
            super(ctx);
        }

        public NewFriendListAdapter(Context context, List<AddRequest> list) {
            super(context, list);
        }

        public void setListener(Listener listener) {
            this.listener = listener;
        }

        @Override
        public View getView(int position, View conView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (conView == null) {
                conView = inflater.inflate(R.layout.contact_add_friend_item,
                        null);
            }
            final AddRequest addRequest = datas.get(position);
            TextView nameView = ViewHolder.findViewById(conView, R.id.name);
            ImageView avatarView = ViewHolder
                    .findViewById(conView, R.id.avatar);
            final Button addBtn = ViewHolder.findViewById(conView, R.id.add);
            View agreedView = ViewHolder.findViewById(conView, R.id.agreedView);

            LeanchatUser from = (LeanchatUser) addRequest.getFromUser();
            ImageLoader.getInstance().displayImage(from.getAvatarUrl(),
                    avatarView, PhotoUtils.avatarImageOptions);
            if (from != null) {
                nameView.setText((String)from.get("realName"));
            }
            int status = addRequest.getStatus();
            if (status == AddRequest.STATUS_WAIT) {
                addBtn.setVisibility(View.VISIBLE);
                agreedView.setVisibility(View.GONE);
                addBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        if (listener != null) {
                            listener.onAgreeAddRequest(addRequest);
                        }
                    }
                });
            } else if (status == AddRequest.STATUS_DONE) {
                addBtn.setVisibility(View.GONE);
                agreedView.setVisibility(View.VISIBLE);
            }
            return conView;
        }

        public interface Listener {
            void onAgreeAddRequest(AddRequest addRequest);
        }
    }
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }
}
