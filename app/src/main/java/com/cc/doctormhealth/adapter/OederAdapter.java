package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.BookAidsActivity;
import com.cc.doctormhealth.model.OederAids;
import com.cc.doctormhealth.utils.Util;
import com.cc.doctormhealth.view.DescHolder;
import com.cc.doctormhealth.view.HeaderHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/2/7 9:25
 * 修改人：Administrator
 * 修改时间：2017/2/7 9:25
 * 修改备注：
 */

public class OederAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DescHolder, RecyclerView.ViewHolder> {
    public List<OederAids.DataEntity> orderList;
    private Context mContext;
    private LayoutInflater mInflater;
    private SparseBooleanArray mBooleanMap;//记录下哪个section是被打开的

    public OederAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(List<OederAids.DataEntity> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return Util.isEmpty(orderList) ? 0 : orderList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = orderList.get(section).getAppointData().size();
        if (count >= 0 && !mBooleanMap.get(section)) {
            count = 0;
        }
        return Util.isEmpty(orderList.get(section).getAppointData()) ? 0 : count;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.order_header, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DescHolder(mInflater.inflate(R.layout.orderaid_list_item, parent, false));
    }

    @Override
    protected void onBindSectionHeaderViewHolder(HeaderHolder holder, final int section) {
        holder.tv_data.setText(orderList.get(section).getClinicTime() + "");
        holder.tv_num.setText("预约" + orderList.get(section).getAppointSize() + "人");
        holder.ll_cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = mBooleanMap.get(section);
                mBooleanMap.put(section, !isOpen);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(DescHolder holder, final int section, final int position) {
        holder.tv_age.setText("" + orderList.get(section).getAppointData().get(position).getAge());
        holder.tv_name.setText("" + orderList.get(section).getAppointData().get(position).getName());
        holder.tv_sex.setText("" + orderList.get(section).getAppointData().get(position).getSex());
        holder.tv_content.setText("" + orderList.get(section).getAppointData().get(position).getCaseness());
        if("0".equals(orderList.get(section).getAppointData().get(position).getAppointStatu())){
            holder.tv_status.setText("未\n到");
            holder.tv_status.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        }else{
            holder.tv_status.setText("已\n到");
            holder.tv_status.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
        }

        ImageLoader.getInstance().displayImage(orderList.get(section).getAppointData().get(position).getUserImage(), holder.iv_head, com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOption);
        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookAidsActivity.class);
                intent.putExtra("id", orderList.get(section).getAppointData().get(position));
                mContext.startActivity(intent);
            }
        });
    }
}
