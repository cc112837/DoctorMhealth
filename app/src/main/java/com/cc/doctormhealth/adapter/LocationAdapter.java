package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.doctormhealth.R;

import java.util.List;

public class LocationAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;

    public LocationAdapter(Context context, List<String> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.location_item, null);
            viewHolder.province = (TextView) convertView.findViewById(R.id.province);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.province.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        public TextView province;
    }

}
