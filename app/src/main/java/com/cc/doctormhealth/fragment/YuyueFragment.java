package com.cc.doctormhealth.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.YuyueAdapter;
import com.cc.doctormhealth.model.Patients;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class YuyueFragment extends Fragment {

    private List<Patients> patientList;
    private YuyueAdapter adapter;
    private ListView list = null;
    private TextView text = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yuyue, container, false);

        patientList = new ArrayList<Patients>();

        init();
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        list = (ListView) getActivity().findViewById(R.id.yuyueList);
        adapter = new YuyueAdapter(getActivity(), patientList);
        text = (TextView) getActivity().findViewById(R.id.number);
        text.setText("已有" + patientList.size() + "名用户预约您");
        list.setAdapter(adapter);
    }

    private void init() {
        patientList.add(new Patients("user", "张三", "27岁", "男", "2015-08-13",
                "被菜刀切到手指，手指上的伤口有..."));
        patientList.add(new Patients("admin", "李四", "29岁", "男", "2015-08-13",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "张小娴", "27岁", "女", "2015-08-13",
                "手臂被烫伤了，有一块红色的皮肤..."));
        patientList.add(new Patients("lifang", "汪诗诗", "18岁", "男", "2015-08-13",
                "前几天做过手术，感觉伤口有点发炎..."));
        patientList.add(new Patients("lifang", "赵帅", "35岁", "女", "2015-08-13",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "黄伟", "22岁", "男", "2015-08-14",
                "手臂被烫伤了，有一块红色的皮肤..."));
        patientList.add(new Patients("lifang", "宋美婷", "22岁", "女", "2015-08-14",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "阙宗英", "22岁", "女", "2015-08-14",
                "被菜刀切到手指，手指上的伤口有..."));
        patientList.add(new Patients("lifang", "张志杰", "22岁", "男", "2015-08-14",
                "高血压"));
        patientList.add(new Patients("lifang", "林志玲", "33岁", "女", "2015-08-14",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "张韶涵", "22岁", "女", "2015-08-15",
                "被菜刀切到手指，手指上的伤口有..."));
        patientList.add(new Patients("lifang", "张淑华", "22岁", "男", "2015-08-15",
                "高血压"));
        patientList.add(new Patients("lifang", "李宇春", "33岁", "女", "2015-08-15",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "张子杰", "22岁", "男", "2015-08-15",
                "高血压"));
        patientList.add(new Patients("lifang", "廖仲恺", "22岁", "男", "2015-08-15",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "秦周涵", "33岁", "女", "2015-08-16",
                "被菜刀切到手指，手指上的伤口有..."));
        patientList.add(new Patients("lifang", "贾宝玉", "22岁", "女", "2015-08-16",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "万旺旺", "22岁", "男", "2015-08-16",
                "被菜刀切到手指，手指上的伤口有..."));
        patientList.add(new Patients("lifang", "李晶晶", "33岁", "女", "2015-08-16",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "房多多", "22岁", "女", "2015-08-16",
                "被菜刀切到手指，手指上的伤口有..."));
        patientList.add(new Patients("lifang", "钱鹏", "22岁", "男", "2015-08-17",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "刘云山", "33岁", "女", "2015-08-17",
                "被菜刀切到手指，手指上的伤口有..."));
        patientList.add(new Patients("lifang", "吴山石", "22岁", "女", "2015-08-18",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "秋思", "22岁", "男", "2015-08-18",
                "手臂被烫伤了，有一块红色的皮肤..."));
        patientList.add(new Patients("lifang", "布吉", "33岁", "女", "2015-08-18",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "庞辉", "22岁", "女", "2015-08-18",
                "手臂被烫伤了，有一块红色的皮肤..."));
        patientList.add(new Patients("lifang", "蒋琬帅", "22岁", "男", "2015-08-18",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "古力娜扎", "22岁", "女",
                "2015-08-19", "手臂被烫伤了，有一块红色的皮肤..."));
        patientList.add(new Patients("lifang", "玉宇少", "22岁", "男", "2015-08-19",
                "头疼发热感觉有点发烧了，请问..."));
        patientList.add(new Patients("lifang", "林国强", "33岁", "女", "2015-08-19",
                "手臂被烫伤了，有一块红色的皮肤..."));

    }

}
