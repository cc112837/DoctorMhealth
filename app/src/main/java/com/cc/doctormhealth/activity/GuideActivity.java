package com.cc.doctormhealth.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.avos.avoscloud.AVAnalytics;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.GuideAdapter;
import com.cc.doctormhealth.fragment.GuideFragment;
import com.cc.doctormhealth.utils.PackageUtils;

import java.util.ArrayList;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/3/31 13:17
*/
public class GuideActivity extends FragmentActivity {
    public GuideActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);

        //记录是否使用过
        SharedPreferences sp = getSharedPreferences("version",MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        //专门获取版本的工具类
        String packageVersion = PackageUtils.getPackageVersion(this);

        editor.putString("version",packageVersion);

        //记得写这句
        editor.commit();

        initView();
    }

    private void initView() {
        /**
         * 获取RadioGroup
         */

        final RadioGroup guide_tabBar = ((RadioGroup) findViewById(R.id.guide_tabBar));

        guide_tabBar.check(R.id.guideButtonOne);

        guide_tabBar.setClickable(false);

        ViewPager viewPager = ((ViewPager) findViewById(R.id.viewPager));

        ArrayList<Fragment> guideFragment = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            GuideFragment fragment = new GuideFragment();

            //Bundle传值，在Fragment中接受，来确定最后一页导航页是否显示“进去看看”
            Bundle bundle = new Bundle();

            bundle.putInt("id",i);

            fragment.setArguments(bundle);

            guideFragment.add(fragment);
        }

        viewPager.setAdapter(new GuideAdapter(getSupportFragmentManager(), guideFragment));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            //设置ViewPager的滑动监听
            @Override
            public void onPageSelected(int i) {
                int checkedId = R.id.guideButtonOne;
                switch (i) {
                    case 0:
                        checkedId = R.id.guideButtonOne;
                        break;
                    case 1:
                        checkedId = R.id.guideButtonTwo;
                        break;
                    case 2:
                        checkedId = R.id.guideButtonThree;
                        break;
                }
                guide_tabBar.check(checkedId);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
