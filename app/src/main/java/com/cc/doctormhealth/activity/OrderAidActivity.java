package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.OederAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.OederAids;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.MyHttpUtils;
import com.cc.doctormhealth.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderAidActivity extends AppCompatActivity {
    private OederAdapter oederAdapter;
    @Bind(R.id.rb_noworder)
    RadioButton rbNoworder;
    @Bind(R.id.rb_hisorder)
    RadioButton rbHisorder;
    @Bind(R.id.rg_title)
    RadioGroup rgTitle;
    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    @Bind(R.id.rv_show)
    RecyclerView rv_show;
    private List<OederAids.DataEntity> dataEntities=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 22:
                    OederAids oederAids=(OederAids) msg.obj;
                    oederAdapter.setData(oederAids.getData());
                    oederAdapter.notifyDataSetChanged();
                break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_aid);
        ButterKnife.bind(this);
        init();
        String url= Constants.SERVER_URL+"PatientAppointServlet";
        User user=new User();
        user.setUsername(LeanchatUser.getCurrentUser().getObjectId());
        user.setAdr("1");
        MyHttpUtils.handData(handler,22,url,user);
        oederAdapter = new OederAdapter(OrderAidActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(OrderAidActivity.this);
        rv_show.setLayoutManager(manager);
        rv_show.setAdapter(oederAdapter);
        rv_show.addItemDecoration(new DividerItemDecoration(
                OrderAidActivity.this, DividerItemDecoration.VERTICAL_LIST));
        oederAdapter.setData(dataEntities);
    }

    private void init() {
        rgTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbNoworder.getId()) {
                    String url= Constants.SERVER_URL+"PatientAppointServlet";
                    User user=new User();
                    user.setUsername(LeanchatUser.getCurrentUser().getObjectId());
                    user.setAdr("1");
                    MyHttpUtils.handData(handler,22,url,user);

                } else if (checkedId == rbHisorder.getId()) {
                    String url= Constants.SERVER_URL+"PatientAppointServlet";
                    User user=new User();
                    user.setUsername(LeanchatUser.getCurrentUser().getObjectId());
                    user.setAdr("2");
                    MyHttpUtils.handData(handler,22,url,user);
                }
            }
        });
    }

    @OnClick({R.id.leftBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftBtn:
                finish();
                break;
        }
    }
}
