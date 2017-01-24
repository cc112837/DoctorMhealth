package com.cc.doctormhealth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import static com.avoscloud.leanchatlib.controller.ChatManager.getContext;

public class SearchActivity extends BaseActivityOfLeanCloud implements PlatformActionListener {
    private EditText searchNameEdit;
    private ImageView left;
    BaseListView<LeanchatUser> listView;
    private String searchName = "";
    private Platform platform_qqFriend;
    private Platform platform_wxFriend;
    private Button searchBtn;
    private AddFriendListAdapter adapter;
    private LinearLayout ll_phone,ll_qq,ll_wechat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ShareSDK.initSDK(getContext());
        init();
    }

    private void init() {
        searchNameEdit = (EditText) findViewById(R.id.searchNameEdit);
        listView = (BaseListView) findViewById(R.id.searchList);
        left = (ImageView) findViewById(R.id.leftBtn);
        ll_phone=(LinearLayout) findViewById(R.id.ll_phone);
        ll_qq=(LinearLayout) findViewById(R.id.ll_qq);
        ll_wechat=(LinearLayout) findViewById(R.id.ll_wechat);
        ll_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map_qqFriend = new HashMap<String, Object>();
                map_qqFriend.put("AppId", "1105828737");
                map_qqFriend.put("AppKey", "Ai4nwhN4ODRLokBF");
                map_qqFriend.put("Enable", true);
                map_qqFriend.put("ShortLinkConversationEnable", "true");
                ShareSDK.setPlatformDevInfo(QQ.NAME, map_qqFriend);
                platform_qqFriend = ShareSDK.getPlatform(SearchActivity.this, QQ.NAME);
                cn.sharesdk.tencent.qq.QQ.ShareParams sp = new cn.sharesdk.tencent.qq.QQ.ShareParams();
                sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
                sp.setTitle("一点就医");
                sp.setText("《一点就医》您身边的健康管理专家");
                sp.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cc.doctormhealth");
                sp.setImageData(null);
                sp.setImageUrl("http://img.wdjimg.com/mms/icon/v1/2/d0/84112fbdf7feb7e9ece19eec1888ad02_256_256.png");
                sp.setImagePath(null);

                platform_qqFriend.setPlatformActionListener(SearchActivity.this); // 设置分享事件回调
                // 执行图文分享
                platform_qqFriend.share(sp);
            }
        });
        ll_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map_wxFriend= new HashMap<String, Object>();
                map_wxFriend.put("AppId", "wx5be800c31e8cad45");
                map_wxFriend.put("Enable", true);
                map_wxFriend.put("BypassApproval", false);
                map_wxFriend.put("ShortLinkConversationEnable", "true");
                ShareSDK.setPlatformDevInfo(Wechat.NAME, map_wxFriend);
                platform_wxFriend = ShareSDK.getPlatform(SearchActivity.this, Wechat.NAME);
                ShareParams sp = new ShareParams();
                sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
                sp.setTitle("一点就医");
                sp.setText("《一点就医》您身边的健康管理专家");
                sp.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cc.doctormhealth");
                sp.setImageData(null);
                sp.setImageUrl("http://img.wdjimg.com/mms/icon/v1/2/d0/84112fbdf7feb7e9ece19eec1888ad02_256_256.png");
                sp.setImagePath(null);
                platform_wxFriend.setPlatformActionListener(SearchActivity.this); // 设置分享事件回调
                // 执行图文分享
                platform_wxFriend.share(sp);
            }
        });
        ll_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(SearchActivity.this,ContactActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Toast.makeText(getContext(),"分享成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(getContext(),"分享失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(getContext(),"取消分享",Toast.LENGTH_LONG).show();
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(getContext());
    }
}
