package com.cc.doctormhealth.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.feedback.FeedbackAgent;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.ChangePwdActivity;
import com.cc.doctormhealth.activity.LoginActivity;
import com.cc.doctormhealth.activity.NotiNewsActivity;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.leanchat.service.PushManager;
import com.cc.doctormhealth.utils.MyAndroidUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/5/19 15:48
*/

public class MeFragment extends Fragment {

    RelativeLayout logout, account, noti_news, about, fankui, share;
    TextView username;
    ImageView headImage;
    String dateTime;
    private AlertDialog avatarDialog;
    private ChatManager chatManager = ChatManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ShareSDK.initSDK(getContext());
        account = (RelativeLayout) view.findViewById(R.id.account);
        chatManager = ChatManager.getInstance();
        noti_news = (RelativeLayout) view.findViewById(R.id.noti_news);
        about = (RelativeLayout) view.findViewById(R.id.about);
        logout = (RelativeLayout) view.findViewById(R.id.logout);
        fankui = (RelativeLayout) view.findViewById(R.id.fankui);
        fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackAgent agent = new FeedbackAgent(getActivity());
                agent.startDefaultThreadActivity();
            }
        });
        share = (RelativeLayout) view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        username = (TextView) view.findViewById(R.id.username);
        username.setText(Constants.USER_NAME);
        headImage = (ImageView) view.findViewById(R.id.headView);
        LeanchatUser curUser = AVUser.getCurrentUser(LeanchatUser.class);
        String avatarUrl = curUser.getAvatarUrl();
        if (avatarUrl == null) {
            try {
                JSONObject object = new JSONObject(curUser.toString());
                JSONObject serverData = object.getJSONObject("serverData");
                JSONObject avatar = serverData.getJSONObject("avatar");
                avatarUrl = avatar.getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        MyAndroidUtil.editXmlByString(
                Constants.icon, avatarUrl);
        ImageLoader.getInstance().displayImage(avatarUrl, headImage,
                com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOption);

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chatManager.closeWithCallback(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                    }
                });
                PushManager.getInstance().unsubscribeCurrentUserChannel();
                AVUser.logOut();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        account.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChangePwdActivity.class);
                getActivity().startActivity(intent);

            }
        });
        noti_news.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotiNewsActivity.class);
                startActivity(intent);

            }
        });
        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(getContext());
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("一点就医");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://dd.myapp.com/16891/AD6CA13CA709A7611A7A0268722A760D.apk");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("一点就医");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://dd.myapp.com/16891/AD6CA13CA709A7611A7A0268722A760D.apk");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("一点就医");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://dd.myapp.com/16891/AD6CA13CA709A7611A7A0268722A760D.apk");
        // 启动分享GUI
        oks.show(getContext());
    }
}
