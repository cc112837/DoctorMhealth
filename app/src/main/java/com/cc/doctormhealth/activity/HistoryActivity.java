package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.HistoryAdapter;
import com.cc.doctormhealth.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity {

    private List<Patient> patientList;
    private HistoryAdapter adapter;
    private ListView list = null;
    private ImageView leftBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_history);

        patientList = new ArrayList<>();
        init();
        leftBtn=(ImageView) findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list = (ListView) this.findViewById(R.id.listHistory);
        adapter = new HistoryAdapter(this, patientList);
        list.setAdapter(adapter);
    }


    private void init() {
        patientList.add(new Patient("lifang", "林志玲", "33岁", "女", "2015-08-14","高血压"));
        patientList.add(new Patient("lifang", "张韶涵", "22岁", "女", "2015-08-15","高血压"));
        patientList.add(new Patient("lifang", "张淑华", "22岁", "男", "2015-08-15","高血压"));
        patientList.add(new Patient("lifang", "李宇春", "33岁", "女", "2015-08-15","高血压"));
        patientList.add(new Patient("lifang", "张子杰", "22岁", "男", "2015-08-15","高血压"));
        patientList.add(new Patient("lifang", "廖仲恺", "22岁", "男", "2015-08-15","高血压"));
        patientList.add(new Patient("lifang", "秦周涵", "33岁", "女", "2015-08-16","高血压"));
        patientList.add(new Patient("lifang", "贾宝玉", "22岁", "女", "2015-08-16","高血压"));
        patientList.add(new Patient("lifang", "万旺旺", "22岁", "男", "2015-08-16","高血压"));
        patientList.add(new Patient("lifang", "李晶晶", "33岁", "女", "2015-08-16","高血压"));
        patientList.add(new Patient("lifang", "房多多", "22岁", "女", "2015-08-16","高血压"));
        patientList.add(new Patient("lifang", "钱鹏", "22岁", "男", "2015-08-17","高血压"));

    }

    public List<Patient> getPatients(List<Patient> patientList) {

        List<Patient> friendsTemp = new ArrayList<>();
        friendsTemp.addAll(patientList);

        patientList = new ArrayList<>(friendsTemp);
        return patientList;
    }
}
