package com.cc.doctormhealth.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.doctormhealth.R;


/**
 * 项目名称：mhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/11/23 10:10
 * 修改人：Administrator
 * 修改时间：2016/11/23 10:10
 * 修改备注：
 */
public class DescHolder extends RecyclerView.ViewHolder {
    public TextView tv_content,tv_name;
    public TextView tv_age, tv_sex;
    public ImageView iv_head,iv_status;
    public LinearLayout ll_content;

    public DescHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        ll_content=(LinearLayout) itemView.findViewById(R.id.ll_content);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        iv_status = (ImageView) itemView.findViewById(R.id.iv_status);
        tv_sex = (TextView) itemView.findViewById(R.id.tv_sex);
        iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
        tv_age = (TextView) itemView.findViewById(R.id.tv_age);
    }
}