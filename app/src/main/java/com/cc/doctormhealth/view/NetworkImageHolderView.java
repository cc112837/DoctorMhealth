package com.cc.doctormhealth.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.cc.doctormhealth.R;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 项目名称：mhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/2/14 14:50
 * 修改人：Administrator
 * 修改时间：2017/2/14 14:50
 * 修改备注：
 */

public class NetworkImageHolderView implements Holder<String> {
    private View view;
    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.banner_item1, null, false);
        return view;
    }
    @Override
    public void UpdateUI(Context context, int position, String data) {
        ImageView imageView=(ImageView) view.findViewById(R.id.sdv_background);
        ImageLoader.getInstance().displayImage(data, imageView, com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOptions);
    }
}
