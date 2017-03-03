package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.EvaluationListAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.UserEvaluation;
import com.cc.doctormhealth.model.UserInfo;
import com.cc.doctormhealth.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 用户评价页面
*/
public class CommentActivity extends Activity  implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private ImageView leftBtn;
    private ListView lv_show;
    private EvaluationListAdapter evaluationListAdapter;
    private RadioGroup rg_all;
    private RadioButton tv_all, tv_satis, tv_nosatis;
    private String flag;
    private List<UserEvaluation.DataEntity> dataEntityList = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 18:
                    UserEvaluation userEvaluation = (UserEvaluation) msg.obj;
                    if (userEvaluation != null) {
                        dataEntityList.clear();
                        dataEntityList.addAll(userEvaluation.getData());
                        evaluationListAdapter.notifyDataSetChanged();
                    } else {

                    }

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pride);
        init();
    }

    private void init() {
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        lv_show = (ListView) findViewById(R.id.lv_show);
        evaluationListAdapter = new EvaluationListAdapter(CommentActivity.this, dataEntityList);
        lv_show.setAdapter(evaluationListAdapter);
        rg_all = (RadioGroup) findViewById(R.id.rg_all);
        tv_all = (RadioButton) findViewById(R.id.tv_all);
        tv_satis = (RadioButton) findViewById(R.id.tv_satis);
        tv_nosatis = (RadioButton) findViewById(R.id.tv_nosatis);
        String url = Constants.SERVER_URL + "MhealthOrderEvaluateCheckServlet";
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("1");
        userInfo.setPass("1");
        rg_all.setOnCheckedChangeListener(this);
        MyHttpUtils.handData(handler, 18, url, userInfo);
        leftBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                finish();
                break;

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == tv_all.getId()) {
            String url = Constants.SERVER_URL + "MhealthOrderEvaluateCheckServlet";
            UserInfo user = new UserInfo();
            user.setPass("1");
            flag = "0";
            user.setPhone(flag);
            MyHttpUtils.handData(handler, 18, url, user);
        } else if (checkedId == tv_satis.getId()) {
            String url = Constants.SERVER_URL + "MhealthOrderEvaluateCheckServlet";
            UserInfo user = new UserInfo();
            user.setPass("1");
            flag = "1";
            user.setPhone(flag);
            MyHttpUtils.handData(handler, 18, url, user);
        } else {//tv_nosatis
            String url = Constants.SERVER_URL + "MhealthOrderEvaluateCheckServlet";
            UserInfo user = new UserInfo();
            user.setPass("1");
            flag = "2";
            user.setPhone(flag);
            MyHttpUtils.handData(handler, 18, url, user);
        }
    }
}
