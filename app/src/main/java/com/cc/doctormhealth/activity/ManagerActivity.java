package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.UserManagerAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.event.MyRecyItemClickListener;
import com.cc.doctormhealth.leanchat.event.MemberLetterEvent;
import com.cc.doctormhealth.leanchat.view.LetterView;
import com.cc.doctormhealth.model.AidManager;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static com.cc.doctormhealth.R.id.rv_list;

public class ManagerActivity extends AppCompatActivity {
    @Bind(rv_list)
    RecyclerView rvList;
    @Bind(R.id.lv_phone)
    LetterView lvPhone;
    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    List<AidManager.DataEntity> entityList=new ArrayList<>();
    LinearLayoutManager layoutManager;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 23:
                    AidManager aidManager = (AidManager) msg.obj;
                    if(aidManager!=null){
                        entityList.addAll(aidManager.getData());
                        userManagerAdapter = new UserManagerAdapter(ManagerActivity.this, entityList);
                        rvList.setAdapter(userManagerAdapter);
                        userManagerAdapter.notifyDataSetChanged();
                        userManagerAdapter.setOnItemClickListener(new MyRecyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int postion) {
                                Intent intent=new Intent(ManagerActivity.this,AidsDetailActivity.class);
                                intent.putExtra("content",entityList.get(postion));
                                startActivity(intent);
                            }
                        });
                    }
                    break;
            }
        }
    };
    private UserManagerAdapter userManagerAdapter;
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        layoutManager = new LinearLayoutManager(ManagerActivity.this);
        rvList.setLayoutManager(layoutManager);

        String url = Constants.SERVER_URL + "PatientControlServlet";
        User user = new User();
        user.setUsername(LeanchatUser.getCurrentUser().getObjectId());
        MyHttpUtils.handData(handler, 23, url, user);
    }
    public void onEvent(MemberLetterEvent event) {
        Character targetChar = Character.toLowerCase(event.letter);
        if (userManagerAdapter.getIndexMap().containsKey(targetChar)) {
            int index = userManagerAdapter.getIndexMap().get(targetChar);
            if (index > 0 && index < userManagerAdapter.getItemCount()) {
                layoutManager.scrollToPositionWithOffset(index, 0);
            }
        }
    }
    @OnClick(R.id.leftBtn)
    public void onClick() {
        finish();
    }
}
