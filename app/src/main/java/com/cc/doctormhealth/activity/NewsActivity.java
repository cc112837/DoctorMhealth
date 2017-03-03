package com.cc.doctormhealth.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.NewsAdapter;
import com.cc.doctormhealth.fragment.HealthListFragment;

import java.util.ArrayList;
import java.util.List;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 资讯页面的列表展示
*/
public class NewsActivity extends AppCompatActivity {
    private ViewPager vp_news;
    private ImageView leftBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        init();
    }

    private void init() {
        leftBtn=(ImageView) findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabid);
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("热点");
        tabLayout.addTab(tab1);
    

        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("医疗");
        tabLayout.addTab(tab3);

   

        TabLayout.Tab tab6 = tabLayout.newTab();
        tab6.setText("行业");
        tabLayout.addTab(tab6);

        vp_news = (ViewPager) findViewById(R.id.vp_news);
        List<Fragment> fragmentList = new ArrayList<>();
        HealthListFragment health1 = new HealthListFragment("1");
       
        HealthListFragment health3 = new HealthListFragment("3");
      
        HealthListFragment health6 = new HealthListFragment("6");

        fragmentList.add(health1);
        fragmentList.add(health3);
  
        fragmentList.add(health6);

        NewsAdapter adapter = new NewsAdapter(getSupportFragmentManager(), fragmentList);
        vp_news.setAdapter(adapter);

        vp_news.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换viewpager
                vp_news.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}


