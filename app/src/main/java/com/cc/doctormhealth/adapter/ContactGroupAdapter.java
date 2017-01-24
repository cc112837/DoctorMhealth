package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.model.ContactBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/1/24 9:53
 * 修改人：Administrator
 * 修改时间：2017/1/24 9:53
 * 修改备注：
 */

public class ContactGroupAdapter extends RecyclerView.Adapter<ContactGroupAdapter.ContractViewHolder> {
    public Context context;
    public List<ContactBean> list;
    private Map<Character, Integer> indexMap = new HashMap<Character, Integer>();

    public ContactGroupAdapter(Context context,List<ContactBean> list) {
        this.context = context;
        this.list=list;
        Collections.sort(list,comparator);
        indexMap = updateIndex(list);
        updateInitialsVisible(list);
    }


    private void updateInitialsVisible(List<ContactBean> list) {
        if (null != list && list.size() > 0) {
            char lastInitial = ' ';
            for (ContactBean item : list) {
                if (!TextUtils.isEmpty(item.getFirstHeadLetter())) {
                    item.setInitialVisible(lastInitial != item.getFirstHeadLetter()
                            .charAt(0));
                    lastInitial = item.getFirstHeadLetter().charAt(0);
                } else {
                    item.setInitialVisible(true) ;
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
    private Map<Character, Integer> updateIndex(List<ContactBean> list) {
        Character lastCharcter = '#';
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (int i = 0; i < list.size(); i++) {
            Character curChar =
                    Character.toLowerCase(list.get(i).getFirstHeadLetter()
                    .charAt(0));
            if (!lastCharcter.equals(curChar)) {
                map.put(curChar, i);
            }
            lastCharcter = curChar;
        }
        return map;
    }
    /**
     * @Mikyou
     * 首字母按a-z排序
     * */
    Comparator<ContactBean> comparator=new Comparator<ContactBean>() {
        @Override
        public int compare(ContactBean t1, ContactBean t2) {
            String a=t1.getFirstHeadLetter();
            String b=t2.getFirstHeadLetter();
            int flag=a.compareTo(b);
            if (flag==0){
                return a.compareTo(b);
            }else{
                return flag;
            }
        }
    };
    @Override
    public ContractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ContractViewHolder holder = new ContractViewHolder(LayoutInflater.from(
                context).inflate(R.layout.contract_all_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ContractViewHolder holder, int position) {
        holder.iv_avtar.setImageResource(list.get(position).getIconId());
        holder.tv_name.setText(list.get(position).getTitle());
        holder.alpha.setVisibility(list.get(position).getInitialVisible() ? View.VISIBLE : View.GONE);
        holder.alpha.setText(String.valueOf(list.get(position).getFirstHeadLetter().charAt(0)));
        holder.tv_show.setText("");
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();}
        else return 0;
    }

    public class ContractViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_avtar;
        private TextView btn_invita,alpha;
        private TextView tv_name, tv_show;

        public ContractViewHolder(View itemView) {
            super(itemView);
            alpha=(TextView) itemView.findViewById(R.id.alpha);
            iv_avtar = ((ImageView) itemView.findViewById(R.id.iv_avtar));
            btn_invita = (TextView) itemView.findViewById(R.id.btn_invita);
            tv_show = (TextView) itemView.findViewById(R.id.tv_show);
            btn_invita.setOnClickListener(this);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_invita:
                    break;
            }
        }
    }
}