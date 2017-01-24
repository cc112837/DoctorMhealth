package com.cc.doctormhealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.model.ContactBean;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    Collator cmp = Collator.getInstance(Locale.SIMPLIFIED_CHINESE);
    private Map<Character, Integer> indexMap = new HashMap<Character, Integer>();

    public ContactGroupAdapter(Context context) {
        this.context = context;
    }
    /**
     * 获取索引 Map
     */
    public Map<Character, Integer> getIndexMap() {
        return indexMap;
    }

    public void setContactList(List<ContactBean> list) {
        Collections.sort(list,comparator);
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
        holder.tv_show.setText("");
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else return 0;
    }

    public class ContractViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_avtar;
        private Button btn_invita;
        private TextView tv_name, tv_show;

        public ContractViewHolder(View itemView) {
            super(itemView);
            iv_avtar = ((ImageView) itemView.findViewById(R.id.iv_avtar));
            btn_invita = (Button) itemView.findViewById(R.id.btn_invita);
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