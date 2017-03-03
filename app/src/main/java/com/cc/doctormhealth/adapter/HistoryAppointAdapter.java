package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.model.HistoryOrder;

import java.util.List;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 历史预约适配器
*/
public class HistoryAppointAdapter extends BaseAdapter {

    private Context context;
    private List<HistoryOrder.DataEntity> list;
    private LayoutInflater mInflater;

    public HistoryAppointAdapter(Context context, List<HistoryOrder.DataEntity> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vholder = null;
        if (view == null) {
            vholder = new ViewHolder();
            view = mInflater.inflate(R.layout.history_item, null);
            vholder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            vholder.tv_case=(TextView) view.findViewById(R.id.tv_case);
            view.setTag(vholder);
        } else {
            vholder = (ViewHolder) view.getTag();
        }
        vholder.tv_case.setText(""+list.get(position).getCaseness());
        vholder.tv_time.setText(""+list.get(position).getClinicTime());
        return view;

    }

    static class ViewHolder {
        public TextView tv_time,tv_case;
    }

}
