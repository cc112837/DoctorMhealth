package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.AidManager;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.MyHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagerActivity extends AppCompatActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 23:
                    AidManager aidManager=(AidManager) msg.obj;
                    break;
            }
        }
    };

    @Bind(R.id.leftBtn)
    ImageView leftBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ButterKnife.bind(this);
        String url= Constants.SERVER_URL+"PatientControlServlet";
        User user=new User();
        user.setUsername(LeanchatUser.getCurrentUser().getObjectId());
        MyHttpUtils.handData(handler,23,url,user);
    }

    @OnClick(R.id.leftBtn)
    public void onClick() {
        finish();
    }
}
