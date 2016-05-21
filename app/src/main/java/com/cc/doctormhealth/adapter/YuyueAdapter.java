package com.cc.doctormhealth.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.model.Patients;

import java.util.List;


public class YuyueAdapter extends BaseAdapter {

    private Context context;
    private List<Patients> list;


    public YuyueAdapter(Context context, List<Patients> list) {

        this.context = context;
        this.list = list;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Patients item = (Patients) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_yuyue, null);
            viewHolder = new ViewHolder();
            viewHolder.dateLayout = (RelativeLayout) convertView.findViewById(R.id.dateLayout);
            viewHolder.lineLayout = (LinearLayout) convertView.findViewById(R.id.lineLayout);
            viewHolder.lineLayout2 = (LinearLayout) convertView.findViewById(R.id.lineLayout2);
            viewHolder.nickView = (TextView) convertView.findViewById(R.id.nickView);
            viewHolder.sexView = (TextView) convertView.findViewById(R.id.sexView);
            viewHolder.sexView1 = (TextView) convertView.findViewById(R.id.sexView1);
            viewHolder.ageView = (TextView) convertView.findViewById(R.id.ageView);
            viewHolder.dateView = (TextView) convertView.findViewById(R.id.dateView);
            viewHolder.miaoshuView = (TextView) convertView.findViewById(R.id.miaoshuView);
            viewHolder.countView = (TextView) convertView.findViewById(R.id.countView);
            viewHolder.headImg = (ImageView) convertView.findViewById(R.id.headImg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nickView.setText(item.username);
        viewHolder.ageView.setText(item.userAge);
        if (item.userSex == "男") {
            viewHolder.sexView1.setText("  ");
            viewHolder.sexView.setText("男");
        } else if (item.userSex == "女") {
            viewHolder.sexView1.setText("女");
            viewHolder.sexView.setText("  ");
        }
        viewHolder.miaoshuView.setText(item.miaoshu);
        viewHolder.ageView.setText(item.userAge);
        // TODO: 2016/5/21 (修改图像)
//        ImgConfig.showHeadImg(item.username, viewHolder.headImg);

        Patients lastPatient = null;

        if (position != 0)
            lastPatient = (Patients) getItem(position - 1);

        if (lastPatient != null && lastPatient.date.equals(item.date)) {
            viewHolder.dateLayout.setVisibility(View.GONE);
            viewHolder.lineLayout2.setVisibility(View.GONE);
            viewHolder.lineLayout.setVisibility(View.GONE);
        } else {
            if (lastPatient == null)
                viewHolder.lineLayout2.setVisibility(View.GONE);
            else
                viewHolder.lineLayout2.setVisibility(View.VISIBLE);
            viewHolder.lineLayout.setVisibility(View.VISIBLE);
            viewHolder.dateLayout.setVisibility(View.VISIBLE);
            viewHolder.dateView.setText(item.date);
            viewHolder.countView.setText("5");
        }

        return convertView;
    }


    class ViewHolder {
        LinearLayout lineLayout, lineLayout2;
        RelativeLayout dateLayout;
        TextView nickView, countView, sexView, sexView1, ageView, dateView, miaoshuView;
        ImageView headImg;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}