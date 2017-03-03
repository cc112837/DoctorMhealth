package com.cc.doctormhealth.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.AboutActivity;
import com.cc.doctormhealth.activity.BarCodeActivity;
import com.cc.doctormhealth.activity.MyMoneyActivity;
import com.cc.doctormhealth.activity.CommentActivity;
import com.cc.doctormhealth.activity.SettingActivity;
import com.cc.doctormhealth.activity.TextActivity;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.utils.MyAndroidUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * 邮箱:cc112837@163.com
 * 创建时间：2016/5/19 15:48
 */

public class MeFragment extends Fragment implements View.OnClickListener {

    RelativeLayout setting, about, pride, share, money;
    TextView username;
    LinearLayout layout_up;
    ImageView headImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ShareSDK.initSDK(getContext());
        setting = (RelativeLayout) view.findViewById(R.id.setting);
        layout_up = (LinearLayout) view.findViewById(R.id.layout_up);
        about = (RelativeLayout) view.findViewById(R.id.about);
        pride = (RelativeLayout) view.findViewById(R.id.pride);
        money = (RelativeLayout) view.findViewById(R.id.money);
        share = (RelativeLayout) view.findViewById(R.id.share);
        username = (TextView) view.findViewById(R.id.username);
        headImage = (ImageView) view.findViewById(R.id.headView);
        LeanchatUser.getCurrentUser().fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                LeanchatUser user = (LeanchatUser) avObject;
                String avatarUrl = user.getAvatarUrl();
                if ("".equals(user.get("realName"))) {
                    username.setText(LeanchatUser.getCurrentUser().getUsername());
                } else {
                    username.setText(user.get("realName") + "");
                }
                MyAndroidUtil.editXmlByString(
                        Constants.icon, avatarUrl);
                ImageLoader.getInstance().displayImage(avatarUrl, headImage,
                        com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOptions);
            }
        });
        money.setOnClickListener(this);
        setting.setOnClickListener(this);
        about.setOnClickListener(this);
        pride.setOnClickListener(this);
        share.setOnClickListener(this);
        layout_up.setOnClickListener(this);
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
        oks.setTitleUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cc.doctormhealth");
        oks.setText("《一点就医》您身边的健康管理专家");
        oks.setImageUrl("http://img.wdjimg.com/mms/icon/v1/2/d0/84112fbdf7feb7e9ece19eec1888ad02_256_256.png");
        oks.setUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cc.doctormhealth");
        oks.setComment("一点就医");
        oks.setSite(getString(R.string.app_name));
        oks.setSiteUrl("http://a.app.qq.com/o/simple.jsp?pkgname=com.cc.doctormhealth");
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
            case R.id.layout_up:
                Intent intenttt = new Intent(getActivity(), BarCodeActivity.class);
                startActivity(intenttt);
                break;
            case R.id.money:
                String check = MyApplication.sharedPreferences.getString(Constants.LOGIN_CHECK,
                        null);
                if ("2".equals(check)) {
                    Intent intent3 = new Intent(getActivity(), MyMoneyActivity.class);
                    startActivity(intent3);
                } else if ("1".equals(check)) {
                    Toast.makeText(getActivity(), "正在认证资质,请稍等", Toast.LENGTH_LONG).show();
                } else if ("3".equals(check)) {
                    Toast.makeText(getActivity(), "资质认证失败,请重新认证", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent6 = new Intent(getActivity(), TextActivity.class);
                    startActivity(intent6);
                }
                break;
            case R.id.pride:
                Intent intent2 = new Intent(getActivity(), CommentActivity.class);
                startActivity(intent2);
                break;
            case R.id.share:
                showShare();
                break;

        }
    }


}
