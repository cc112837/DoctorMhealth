package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.EvaluationListAdapter;
import com.cc.doctormhealth.adapter.LocationAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.UserEvaluation;
import com.cc.doctormhealth.model.UserInfo;
import com.cc.doctormhealth.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class PrideActivity extends Activity implements View.OnClickListener {
    private ImageView leftBtn;
    private ListView lv_show, locationListView;
    private PopupWindow mPopupWindow;
    private EvaluationListAdapter evaluationListAdapter;
    private LinearLayout total_location, layout_left;
    private TextView text_address;
    private List<UserEvaluation.DataEntity> dataEntityList = new ArrayList<>();
    private List<String> locationList = new ArrayList<>();
    private LocationAdapter locationAdapter;
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
        total_location = (LinearLayout) findViewById(R.id.total_location);
        evaluationListAdapter = new EvaluationListAdapter(PrideActivity.this, dataEntityList);
        lv_show.setAdapter(evaluationListAdapter);
        String url = Constants.SERVER_URL + "MhealthOrderEvaluateCheckServlet";
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(LeanchatUser.getCurrentUser().getObjectId()+ "");
        userInfo.setPass("1");
        MyHttpUtils.handData(handler, 18, url, userInfo);
        leftBtn.setOnClickListener(this);
        total_location.setOnClickListener(this);
    }

    private void showPopupWindow(int width, int height) {
        layout_left = (LinearLayout) LayoutInflater.from(
                PrideActivity.this).inflate(R.layout.popup_category, null);
        locationListView = (ListView) layout_left
                .findViewById(R.id.rootcategory);
        locationList.add("全部评价");
        locationList.add("近三个月");
        locationAdapter = new LocationAdapter(PrideActivity.this, locationList);
        locationListView.setAdapter(locationAdapter);
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mPopupWindow.dismiss();
                text_address.setText("全部");

            }
        });

        mPopupWindow = new PopupWindow(layout_left, width, height * 2 / 3, true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                text_address = (TextView) total_location.findViewById(R.id.text_address);
                text_address.setTextColor(0xff000000);
            }
        });
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAsDropDown(total_location, 5, 1);
        mPopupWindow.update();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                finish();
                break;
            case R.id.total_location:
                text_address.setTextColor(0xf000cf00);
                showPopupWindow(lv_show.getWidth(),
                        lv_show.getHeight());
                break;
        }
    }
}
