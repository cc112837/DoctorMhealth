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
import com.cc.doctormhealth.adapter.AppointAdapter;
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
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 患者预约页面
*/
public class PatientAppointmentActivity extends AppCompatActivity {
    private AppointAdapter appointAdapter;
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
                    appointAdapter.setData(oederAids.getData());
                    appointAdapter.notifyDataSetChanged();
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
        appointAdapter = new AppointAdapter(PatientAppointmentActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(PatientAppointmentActivity.this);
        rv_show.setLayoutManager(manager);
        rv_show.setAdapter(appointAdapter);
        rv_show.addItemDecoration(new DividerItemDecoration(
                PatientAppointmentActivity.this, DividerItemDecoration.VERTICAL_LIST));
        appointAdapter.setData(dataEntities);
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
