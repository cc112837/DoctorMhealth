package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.NewsAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends AppCompatActivity {

    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    @Bind(R.id.lv_show)
    ListView lvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(i+"");
        }
        NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, list);
        lvShow.setAdapter(newsAdapter);
    }

    @OnClick(R.id.leftBtn)
    public void onClick() {
        finish();
    }
}
