package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.model.Bookmanger;
import com.cc.doctormhealth.utils.Util;

import java.util.List;

/**
 * 项目名称：mhealth
 * 类描述：商品首页的适配器
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/9/14 13:48
 * 修改人：Administrator
 * 修改时间：2016/11/19 8:38
 * 修改备注：
 */
public class ManageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<Bookmanger.DataEntity> list;

    public ManageAdapter(Context context, List<Bookmanger.DataEntity> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Util.getInstance().isEmpty(list) ? 0 : list.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.book_manger_item, null);
            viewHolder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            viewHolder.tv_hosi=(TextView) convertView.findViewById(R.id.tv_hosi);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_hosi.setText(""+list.get(position).getMedicalInstitution());
        viewHolder.tv_data.setText(""+list.get(position).getMedicalDate());
        viewHolder.tv_num.setText(""+list.get(position).getMedicalType());
        return convertView;
    }

    static class ViewHolder {
        public TextView tv_data, tv_num,tv_hosi;
    }
}
