package com.cc.doctormhealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 资讯顶部tab适配器
*/
public class NewsAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public NewsAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        int i = 0;
        if (fragmentList != null) {
            i = fragmentList.size();
        }
        return i;
    }
}
