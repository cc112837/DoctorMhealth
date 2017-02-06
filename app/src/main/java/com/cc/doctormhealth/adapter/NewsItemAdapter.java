package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.model.NewsYang;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 项目名称：mhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/6/23 14:35
 * 修改人：Administrator
 * 修改时间：2016/6/23 14:35
 * 修改备注：
 */
public class NewsItemAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private Context context;
    private List<NewsYang.DataEntity> list;

    public NewsItemAdapter(Context context, List<NewsYang.DataEntity> list) {
        mInflater = LayoutInflater.from(context);
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        if(list!=null)
            return  list.size();
        else
        return  0 ;
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
            convertView = mInflater.inflate(R.layout.news_item, null);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getMedicalImage(), viewHolder.iv_img, PhotoUtils.avatarImage);
        viewHolder.tv_title.setText(list.get(position).getMedicalTitle());
        return convertView;
    }
   class ViewHolder {
        public ImageView iv_img;
        public  TextView tv_title;

    }

}
