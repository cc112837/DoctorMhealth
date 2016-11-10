package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.model.Money;

import java.util.List;

public class MoneyAdapter extends BaseAdapter {

    private Context context;
    private List<Money.DataEntity> list;
    private LayoutInflater mInflater;

    public MoneyAdapter(Context context, List<Money.DataEntity> list) {
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
            view = mInflater.inflate(R.layout.money_item, null);
            vholder.tv_item = (TextView) view.findViewById(R.id.tv_item);
            vholder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            view.setTag(vholder);
        } else {
            vholder = (ViewHolder) view.getTag();
        }
        vholder.tv_item.setText(""+list.get(position).getTime());
        vholder.tv_price.setText("+¥"+list.get(position).getPrice() );
        return view;

    }

    static class ViewHolder {
        public TextView tv_item;
        public TextView tv_price;
    }

}
