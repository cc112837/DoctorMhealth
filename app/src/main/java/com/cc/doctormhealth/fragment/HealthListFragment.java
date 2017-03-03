package com.cc.doctormhealth.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.avos.avoscloud.AVAnalytics;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.NewsDetailActivity;
import com.cc.doctormhealth.adapter.NewsItemAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.leanchat.view.XListView;
import com.cc.doctormhealth.model.NewsList;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯详情页面的页面主体
 */
public class HealthListFragment extends Fragment implements XListView.IXListViewListener {
    String contentid;
    public HealthListFragment(String contentid) {
        this.contentid = contentid;
    }

    private XListView lv_show;
    private NewsItemAdapter adapter;
    int page;
    private List<NewsList.DataEntity> list;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 21:
                    if (page == 1) {
                        list.clear();
                    }
                    NewsList newsYang = (NewsList) msg.obj;
                    lv_show.stopRefresh();
                    list.addAll(newsYang.getData());
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onStart() {
        super.onStart();
    }

    public void onPause() {
        super.onPause();
        AVAnalytics.onFragmentEnd("health-list-fragment");
    }

    public void onResume() {
        super.onResume();
        AVAnalytics.onFragmentStart("health-list-fragment");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_health_list_fragment, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        lv_show = (XListView) v.findViewById(R.id.lv_show);
        lv_show.setPullLoadEnable(true);
        lv_show.setPullRefreshEnable(true);
        lv_show.setXListViewListener(this);
        list = new ArrayList<>();
        adapter = new NewsItemAdapter(getContext(), list);
        lv_show.setAdapter(adapter);
        lv_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("content", list.get(position-1).getMedicalId()+"");
                startActivity(intent);
            }
        });
        page = 1;
        String url= Constants.SERVER_URL+"MedicalArticleServlet";
        User user=new User();
        user.setUsername(contentid+"");
        user.setAdr(page+"");
        MyHttpUtils.handData(handler, 21, url, user);
    }


    @Override
    public void onRefresh() {
        page = 1;
        String url= Constants.SERVER_URL+"MedicalArticleServlet";
        User user=new User();
        user.setUsername(contentid+"");
        user.setAdr(page+"");
        MyHttpUtils.handData(handler, 21, url, user);
    }

    @Override
    public void onLoadMore() {
        page++;
        String url= Constants.SERVER_URL+"MedicalArticleServlet";
        User user=new User();
        user.setUsername(contentid+"");
        user.setAdr(page+"");
        MyHttpUtils.handData(handler, 21, url, user);
    }
}
