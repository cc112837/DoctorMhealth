package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.HistoryAppointAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.HistoryOrder;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.MyHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cc.doctormhealth.R.id.lv_show;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 历史预约
*/
public class HistoryAppointmentActivity extends AppCompatActivity {

    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    @Bind(lv_show)
    ListView lvShow;
    private List<HistoryOrder.DataEntity> list = new ArrayList<>();
    private HistoryAppointAdapter historyAppointAdapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 27:
                    HistoryOrder historyOrder=(HistoryOrder) msg.obj;
                    if (historyOrder!=null){
                        list.addAll(historyOrder.getData());
                        historyAppointAdapter.notifyDataSetChanged();
                    }

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("id");
        String url= Constants.SERVER_URL+"PatientControlHistoryServlet";
        User user=new User();
        user.setUsername(id);
        MyHttpUtils.handData(handler,27,url,user);
        historyAppointAdapter = new HistoryAppointAdapter(this, list);
        lvShow.setAdapter(historyAppointAdapter);

    }

    @OnClick(R.id.leftBtn)
    public void onClick() {
        finish();
    }
}
