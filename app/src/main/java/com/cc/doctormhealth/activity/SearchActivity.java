package com.cc.doctormhealth.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.view.ViewHolder;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.activity.BaseActivityOfLeanCloud;
import com.cc.doctormhealth.leanchat.adapter.BaseListAdapter;
import com.cc.doctormhealth.leanchat.service.AddRequestManager;
import com.cc.doctormhealth.leanchat.service.CacheService;
import com.cc.doctormhealth.leanchat.view.BaseListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivityOfLeanCloud {
    private EditText searchNameEdit;
    private ImageView left;
    BaseListView<LeanchatUser> listView;
    private String searchName = "";
    private Button searchBtn;
    private AddFriendListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init() {
        searchNameEdit = (EditText) findViewById(R.id.searchNameEdit);
        listView = (BaseListView) findViewById(R.id.searchList);
        left = (ImageView) findViewById(R.id.leftBtn);
        left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchName = searchNameEdit.getText().toString();
                listView.onRefresh();
            }
        });
        initActionBar(MyApplication.getInstance().getString(
                R.string.contact_findFriends));
        adapter = new AddFriendListAdapter(this, new ArrayList<LeanchatUser>());
        listView.init(new BaseListView.DataFactory<LeanchatUser>() {
            @Override
            public List<LeanchatUser> getDatasInBackground(int skip, int limit,
                                                           List<LeanchatUser> currentDatas) throws Exception {
                return searchUser(searchName, adapter.getCount());
            }
        }, adapter);
        adapter.setClickListener(new AddFriendListAdapter.AddButtonClickListener() {
            @Override
            public void onAddButtonClick(LeanchatUser user) {
                AddRequestManager.getInstance().createAddRequestInBackground(
                        SearchActivity.this, user);

            }
        });
        listView.onRefresh();
    }

    public List<LeanchatUser> searchUser(String searchName, int skip)
            throws AVException {
        AVQuery<LeanchatUser> q = AVUser.getQuery(LeanchatUser.class);
//		AVQuery<LeanchatUser> q = LeanchatUser.getQuery(LeanchatUser.class);
        q.whereContains(LeanchatUser.USERNAME, searchName);
        q.limit(Constants.PAGE_SIZE);
        q.skip(skip);
        LeanchatUser user = LeanchatUser.getCurrentUser();
        List<String> friendIds = new ArrayList<String>();
        friendIds.addAll(CacheService.getFriendIds());
        friendIds.add(user.getObjectId());
        q.whereNotContainedIn(Constants.OBJECT_ID, friendIds);
        q.whereEqualTo("property", "doctor");
        q.orderByDescending(Constants.UPDATED_AT);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        List<LeanchatUser> users = q.find();
        CacheService.registerUsers(users);
        return users;
    }

    public static class AddFriendListAdapter extends
            BaseListAdapter<LeanchatUser> {

        private AddButtonClickListener addButtonClickListener;

        public AddFriendListAdapter(Context context, List<LeanchatUser> list) {
            super(context, list);
        }

        public void setClickListener(
                AddButtonClickListener addButtonClickListener) {
            this.addButtonClickListener = addButtonClickListener;
        }

        @Override
        public View getView(int position, View conView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (conView == null) {
                conView = inflater.inflate(R.layout.contact_add_friend_item,
                        null);
            }
            final LeanchatUser user = (LeanchatUser) datas.get(position);
            TextView nameView = ViewHolder.findViewById(conView, R.id.name);
            ImageView avatarView = ViewHolder
                    .findViewById(conView, R.id.avatar);
            Button addBtn = ViewHolder.findViewById(conView, R.id.add);
            ImageLoader
                    .getInstance()
                    .displayImage(
                            user.getAvatarUrl(),
                            avatarView,
                            com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOptions);
            nameView.setText(user.getUsername());
            addBtn.setText(R.string.contact_add);
            addBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (addButtonClickListener != null) {
                        addButtonClickListener.onAddButtonClick(user);
                    }
                }
            });
            return conView;
        }

        public interface AddButtonClickListener {
            void onAddButtonClick(LeanchatUser user);
        }

    }
}
