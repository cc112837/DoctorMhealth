package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.CaseHistoryAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.CaseHistory;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CaseHisManagerActivity extends AppCompatActivity {
    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    @Bind(R.id.lv_show)
    ListView lvShow;
    @Bind(R.id.tv_con)
    TextView tvCon;

    private List<CaseHistory.DataEntity> list = new ArrayList<>();
    CaseHistoryAdapter caseHistoryAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 24:
                    CaseHistory bookmanger = (CaseHistory) msg.obj;
                    list.clear();
                    list.addAll(bookmanger.getData());
                    if (list.size() == 0) {
                        tvCon.setVisibility(View.VISIBLE);
                    } else {
                        tvCon.setVisibility(View.GONE);
                        caseHistoryAdapter.notifyDataSetChanged();
                        lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(CaseHisManagerActivity.this, ImageScannerActivity.class);
                                intent.putStringArrayListExtra("name", (ArrayList<String>) list.get(position).getMdicalPicture());
                                startActivity(intent);
                            }
                        });
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        ButterKnife.bind(this);
        caseHistoryAdapter = new CaseHistoryAdapter(CaseHisManagerActivity.this, list);
        lvShow.setAdapter(caseHistoryAdapter);
        String id = getIntent().getStringExtra("id");
        User user = new User();
        user.setUsername(id);
        String url = Constants.SERVER_URL + "PatientCaseManageByIdServlet";
        MyHttpUtils.handData(handler, 24, url, user);
    }

    @OnClick(R.id.leftBtn)
    public void onClick() {
        finish();
    }
}
