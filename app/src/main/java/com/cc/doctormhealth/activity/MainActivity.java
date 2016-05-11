package com.cc.doctormhealth.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.fragment.ChangeFragmentHelper;
import com.cc.doctormhealth.fragment.ContactFragment;
import com.cc.doctormhealth.fragment.MeFragment;
import com.cc.doctormhealth.fragment.MessageFragment;
import com.cc.doctormhealth.fragment.YuyueFragment;
import com.cc.doctormhealth.leanchat.service.CacheService;
import com.cc.doctormhealth.utils.MyAndroidUtil;
import com.cc.doctormhealth.utils.Tool;

public class MainActivity extends AppCompatActivity {

    private ImageView rightBtn;
    private TextView countView,countView1,titleView;
    private FrameLayout titlebar;
    private long exitTime = 0; // 两次按返回键退出用
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        initView();
        Fragment fragment = new MessageFragment();
        ChangeFragmentHelper helper = new ChangeFragmentHelper();
        helper.setTargetFragment(fragment);
        helper.setIsClearStackBack(true);
        changeFragment(helper);
        updateCount();
        updateCount1();
        CacheService.registerUser(AVUser.getCurrentUser(LeanchatUser.class));
    }


    private void initView() {
        titlebar = (FrameLayout) findViewById(R.id.titlebar);
        countView = (TextView) findViewById(R.id.countView);
        countView1 = (TextView) findViewById(R.id.countView1);
        titleView = (TextView) findViewById(R.id.titleView);
        rightBtn= (ImageView) findViewById(R.id.rightBtn);
        rightBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));

            }
        });
        //盛放Fragment的容器
        FrameLayout main_container = ((FrameLayout) findViewById(R.id.main_container));

        RadioGroup main_tabBar = ((RadioGroup) findViewById(R.id.main_tabBar));

        main_tabBar.check(R.id.main_relation);

        fragment = null;
        titlebar.setVisibility(View.VISIBLE);
        rightBtn.setVisibility(View.GONE);

        main_tabBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_relation:
                        fragment = new MessageFragment();
                        titlebar.setVisibility(View.VISIBLE);
                        rightBtn.setVisibility(View.GONE);
                        titleView.setText("消息");
                        break;
                    case R.id.main_strategy:
                        fragment = new YuyueFragment();
                        titlebar.setVisibility(View.VISIBLE);
                        rightBtn.setVisibility(View.GONE);
                        titleView.setText("预约");
                        break;
                    case R.id.main_discover:
                        fragment = new ContactFragment();
                        titlebar.setVisibility(View.VISIBLE);
                        rightBtn.setVisibility(View.VISIBLE);
                        titleView.setText("医圈");
                        break;
                    case R.id.main_person:
                        fragment = new MeFragment();
                        titlebar.setVisibility(View.GONE);
                        rightBtn.setVisibility(View.GONE);
                        break;

                }

                ChangeFragmentHelper helper = new ChangeFragmentHelper();
                helper.setTargetFragment(fragment);
                helper.setIsClearStackBack(true);
                changeFragment(helper);
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Tool.initToast(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
        }
        else if (KeyEvent.KEYCODE_VOLUME_DOWN == keyCode) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        } else if (KeyEvent.KEYCODE_VOLUME_UP == keyCode) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    protected void onResume() {
        MyAndroidUtil.clearNoti();
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void updateCount() {
        // 更新界面
        int count = 0 ;//= NewMsgDbHelper.getInstance(getApplicationContext()).getMsgCount();
        if (count > 0) {
            countView.setVisibility(View.VISIBLE);
            countView.setText(""+count);
        }
        else {
            countView.setVisibility(View.GONE);
        }
    }
    public void updateCount(int count ) {
        // 更新界面//= NewMsgDbHelper.getInstance(getApplicationContext()).getMsgCount();
        if (count>0) {
            countView.setVisibility(View.VISIBLE);
            countView.setText(""+count);
        }
        else {
            countView.setVisibility(View.GONE);
        }
    }

    public void updateCount1() {
        // 更新界面
        int count = 0 ;//= NewMsgDbHelper.getInstance(getApplicationContext()).getMsgCount(""+0);
        if (count>0) {
            countView1.setVisibility(View.VISIBLE);
            countView1.setText(""+count);
        }
        else {
            countView1.setVisibility(View.GONE);
        }
    }
    public void history(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    public void changeFragment(ChangeFragmentHelper helper) {

        //获取需要跳转的Fragment
        Fragment targetFragment = helper.getTargetFragment();
        //获取是否清空回退栈
        boolean clearStackBack = helper.isClearStackBack();
        //获取是否加入回退栈
        String targetFragmentTag = helper.getTargetFragmentTag();
        //获取Bundle
        Bundle bundle = helper.getBundle();
        //是否给Fragment传值
        if (bundle != null) {
            targetFragment.setArguments(bundle);
        }

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        if (targetFragment != null) {
            fragmentTransaction.replace(R.id.main_container, targetFragment);
        }

        //是否添加到回退栈
        if (targetFragmentTag != null) {
            fragmentTransaction.addToBackStack(targetFragmentTag);
        }

        //是否清空回退栈
        if (clearStackBack) {
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        fragmentTransaction.commit();
    }

}
