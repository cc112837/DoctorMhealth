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
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;

    public NewsAdapter(Context context, List<String> list) {
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
            view = mInflater.inflate(R.layout.news_item, null);
            vholder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            vholder.iv_image = (ImageView) view.findViewById(R.id.iv_image);
            view.setTag(vholder);
        } else {
            vholder = (ViewHolder) view.getTag();
        }
        vholder.tv_title.setText("**"+position);
        ImageLoader.getInstance().displayImage("", vholder.iv_image, PhotoUtils.avatarImageOptions);
        return view;

    }

    static class ViewHolder {
        public TextView tv_title;
        public ImageView iv_image;
    }

}
