package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.MoneyAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.Money;
import com.cc.doctormhealth.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class MyMoneyActivity extends Activity implements View.OnClickListener {
    private TextView tv_aid;
    private ImageView leftBtn;
    private TextView tv_total;
    private ListView lv_show;
    private List<Money.DataEntity> list = new ArrayList<>();
    private MoneyAdapter moneyAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 19:
                    Money money = (Money) msg.obj;
                    tv_total.setText("¥" + String.format("%.2f", money.getTotalPrice()));
                    list.clear();
                    list.addAll(money.getData());
                    moneyAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        init();
    }

    private void init() {
        tv_aid = (TextView) findViewById(R.id.tv_aid);
        tv_aid.setOnClickListener(this);
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(this);
        tv_total = (TextView) findViewById(R.id.tv_total);
        lv_show = (ListView) findViewById(R.id.lv_show);
        moneyAdapter = new MoneyAdapter(this, list);
        lv_show.setAdapter(moneyAdapter);
        String url = Constants.SERVER_URL + "MhealthDoctorBalanceServlet";
        MyHttpUtils.handData(handler, 19, url, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_aid:
                new AlertDialog.Builder(MyMoneyActivity.this).setTitle("提示")
                        .setMessage("您确定要提交提现申请吗").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MyMoneyActivity.this,"您的提现申请已经提交，请耐心等待。",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.leftBtn:
                finish();
                break;
        }
    }
}
