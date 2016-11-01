package com.cc.doctormhealth.fragment;


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
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.AboutActivity;
import com.cc.doctormhealth.activity.MyMoneyActivity;
import com.cc.doctormhealth.activity.PrideActivity;
import com.cc.doctormhealth.activity.SettingActivity;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.utils.MyAndroidUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 邮箱:cc112837@163.com
 * 创建时间：2016/5/19 15:48
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    RelativeLayout setting, about, pride, share, money;
    TextView username;
    ImageView headImage;
    private ChatManager chatManager = ChatManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ShareSDK.initSDK(getContext());
        chatManager = ChatManager.getInstance();
        setting = (RelativeLayout) view.findViewById(R.id.setting);
        about = (RelativeLayout) view.findViewById(R.id.about);
        pride = (RelativeLayout) view.findViewById(R.id.pride);
        money = (RelativeLayout) view.findViewById(R.id.money);
        share = (RelativeLayout) view.findViewById(R.id.share);
        username = (TextView) view.findViewById(R.id.username);
        username.setText(MyApplication.sharedPreferences.getString(Constants.LOGIN_ACCOUNT,
                null));
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

        money.setOnClickListener(this);
        setting.setOnClickListener(this);
        about.setOnClickListener(this);
        pride.setOnClickListener(this);
        share.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(getContext());
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("一点就医");
        oks.setTitleUrl("http://dd.myapp.com/16891/AD6CA13CA709A7611A7A0268722A760D.apk");
        oks.setText("一点就医");
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setUrl("http://dd.myapp.com/16891/AD6CA13CA709A7611A7A0268722A760D.apk");
        oks.setComment("一点就医");
        oks.setSite(getString(R.string.app_name));
        oks.setSiteUrl("http://dd.myapp.com/16891/AD6CA13CA709A7611A7A0268722A760D.apk");
        oks.show(getContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                Intent intent1 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.money:
                Intent intent3 = new Intent(getActivity(), MyMoneyActivity.class);
                startActivity(intent3);
                break;
            case R.id.pride:
                Intent intent2 = new Intent(getActivity(), PrideActivity.class);
                startActivity(intent2);
                break;
            case R.id.share:
                showShare();
                break;

        }
    }
}
