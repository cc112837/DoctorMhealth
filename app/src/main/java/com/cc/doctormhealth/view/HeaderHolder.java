package com.cc.doctormhealth.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.doctormhealth.R;


/**
 * 项目名称：mhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2016/11/23 9:47
 * 修改人：Administrator
 * 修改时间：2016/11/23 9:47
 * 修改备注：
 */
public class HeaderHolder extends RecyclerView.ViewHolder {
    public ImageView iv_jiantou;
    public TextView tv_num, tv_data;

    public HeaderHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        tv_num=(TextView) itemView.findViewById(R.id.tv_num);
        tv_data=(TextView) itemView.findViewById(R.id.tv_data);
        iv_jiantou = (ImageView) itemView.findViewById(R.id.iv_jiantou);
    }
}
