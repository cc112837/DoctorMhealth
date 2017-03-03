package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.event.MyRecyItemClickListener;
import com.cc.doctormhealth.leanchat.pinyin.PinyinHelper;
import com.cc.doctormhealth.model.AidManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：DoctorMhealth
 * 类描述：用户管理适配器
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/1/24 9:53
 * 修改人：Administrator
 * 修改时间：2017/1/24 9:53
 * 修改备注：
 */

public class UserManagerAdapter extends RecyclerView.Adapter<UserManagerAdapter.ManagerViewHolder> {
    public Context context;
    public List<AidManager.DataEntity> list;
    private MyRecyItemClickListener listener;
    private Map<Character, Integer> indexMap = new HashMap<Character, Integer>();

    public void setOnItemClickListener(MyRecyItemClickListener listener) {
        this.listener = listener;
    }

    public UserManagerAdapter(Context context, List<AidManager.DataEntity> list) {
        this.context = context;
        this.list = list;
        Collections.sort(list, comparator);
        indexMap = updateIndex(list);
        updateInitialsVisible(list);
    }


    private void updateInitialsVisible(List<AidManager.DataEntity> list) {
        if (null != list && list.size() > 0) {
            char lastInitial = ' ';
            for (AidManager.DataEntity item : list) {
                if (!TextUtils.isEmpty(PinyinHelper.getShortPinyin(item.getName()))) {
                    item.setInitialVisible(lastInitial != PinyinHelper.getShortPinyin(item.getName())
                            .charAt(0));
                    lastInitial = PinyinHelper.getShortPinyin(item.getName()).charAt(0);
                } else {
                    item.setInitialVisible(true);
                    lastInitial = ' ';
                }
            }
        }
    }

    /**
     * 获取索引 Map
     */
    public Map<Character, Integer> getIndexMap() {
        return indexMap;
    }


    /**
     * 更新索引 Map
     */
    private Map<Character, Integer> updateIndex(List<AidManager.DataEntity> list) {
        Character lastCharcter = '#';
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < list.size(); i++) {
            Character curChar =
                    Character.toLowerCase(PinyinHelper.getShortPinyin(list.get(i).getName()
                    ) .charAt(0));
            if (!lastCharcter.equals(curChar)) {
                map.put(curChar, i);
            }
            lastCharcter = curChar;
        }
        return map;
    }

    /**
     * @Mikyou 首字母按a-z排序
     */
    Comparator<AidManager.DataEntity> comparator = new Comparator<AidManager.DataEntity>() {
        @Override
        public int compare(AidManager.DataEntity t1, AidManager.DataEntity t2) {
            String a = PinyinHelper.getShortPinyin(t1.getName());
            String b = PinyinHelper.getShortPinyin(t2.getName());
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    @Override
    public ManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ManagerViewHolder holder = new ManagerViewHolder(LayoutInflater.from(
                context).inflate(R.layout.usermanger_all_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ManagerViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.iv_show.setVisibility(View.GONE);
        ImageLoader.getInstance().displayImage(list.get(position).getUserImage(),holder.iv_head, com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOption);
        holder.alpha.setVisibility(list.get(position).getInitialVisible() ? View.VISIBLE : View.GONE);
        holder.alpha.setText(String.valueOf(PinyinHelper.getShortPinyin(list.get(position).getName()).charAt(0)));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else return 0;
    }

    public class ManagerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView  alpha;
        private TextView tv_name;
        public ImageView iv_head;
        public ImageView iv_show;
        private LinearLayout ll_item;

        public ManagerViewHolder(View itemView) {
            super(itemView);
            alpha = (TextView) itemView.findViewById(R.id.alpha);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            iv_show=(ImageView) itemView.findViewById(R.id.iv_show);
            ll_item.setOnClickListener(this);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_item:
                    if (listener != null) {
                        listener.onItemClick(v, getLayoutPosition());
                    }

                    break;
            }
        }
    }
}