package com.cc.doctormhealth.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.FriendActivity;
import com.cc.doctormhealth.constant.ImgConfig;
import com.cc.doctormhealth.model.Patient;

import java.util.List;


public class HistoryAdapter extends BaseAdapter {
	
	private List<Patient> list;
	Context context;
	public HistoryAdapter(Context context, List<Patient> list) {
		
		this.context = context;
		this.list = list;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		final Patient item = (Patient) getItem(position);	
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.row_ask, null);
			viewHolder = new ViewHolder();
			viewHolder.nickView = (TextView) convertView.findViewById(R.id.nickView);
			viewHolder.headImg = (ImageView) convertView.findViewById(R.id.headImg);
			viewHolder.ageView = (TextView) convertView.findViewById(R.id.ageView);
			viewHolder.bingView = (TextView) convertView.findViewById(R.id.bingView);
			viewHolder.sexView = (TextView) convertView.findViewById(R.id.sexView);
			viewHolder.sexView1 = (TextView)convertView.findViewById(R.id.sexView1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.nickView.setText(item.username);
		viewHolder.ageView.setText(item.userAge);
		viewHolder.bingView.setText(item.bing);
		if(item.userSex == "男"){
			viewHolder.sexView1.setText("   ");
			viewHolder.sexView.setText("男");
		}
		else if(item.userSex == "女"){
			viewHolder.sexView1.setText("女");
			viewHolder.sexView.setText("   ");
		}
		
		ImgConfig.showHeadImg(item.username, viewHolder.headImg);
		viewHolder.headImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, FriendActivity.class);
				intent.putExtra("username", item.username);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
			}
		});
			
		
		return convertView;
	}
	
	
	class ViewHolder {
		TextView nickView,ageView,sexView,sexView1,bingView;
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