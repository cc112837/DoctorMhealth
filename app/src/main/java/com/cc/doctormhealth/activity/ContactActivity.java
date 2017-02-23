package com.cc.doctormhealth.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.ContactGroupAdapter;
import com.cc.doctormhealth.event.MyRecyItemClickListener;
import com.cc.doctormhealth.leanchat.event.MemberLetterEvent;
import com.cc.doctormhealth.leanchat.model.ContactBean;
import com.cc.doctormhealth.leanchat.service.ContactInfoService;
import com.cc.doctormhealth.leanchat.view.LetterView;

import java.util.List;

import de.greenrobot.event.EventBus;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView leftBtn;
    LinearLayoutManager layoutManager;
    private RecyclerView rv_list;
    private LetterView lv_phone;
    private ContactGroupAdapter contactGroupAdapter;
    private List<ContactBean> contactList;

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        EventBus.getDefault().register(this);
        init();
    }

    private void init() {
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        lv_phone = (LetterView) findViewById(R.id.lv_phone);

        layoutManager = new LinearLayoutManager(ContactActivity.this);
        rv_list.setLayoutManager(layoutManager);

        ContactInfoService mContactInfoService = new ContactInfoService(this);
        //返回手机联系人对象集合
        contactList = mContactInfoService.getContactList();
        contactGroupAdapter = new ContactGroupAdapter(ContactActivity.this,contactList);
        rv_list.setAdapter(contactGroupAdapter);
        contactGroupAdapter.setOnItemClickListener(new MyRecyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                String smsBody = "我正在使用【一点就医医生版】APP，通过一点就医可以拉近医患距离，让彼此感到心的温暖。点击下载:" + "http://a.app.qq.com/o/simple.jsp?pkgname=com.cc.doctormhealth"+"[康泽云]";
                Uri smsToUri = Uri.parse("smsto:");
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
                sendIntent.putExtra("address",contactList.get(postion).getPhoneNum()); // 电话号码，这行去掉的话，默认就没有电话
                sendIntent.putExtra("sms_body", smsBody);
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivityForResult(sendIntent,1002);
            }
        });
        leftBtn.setOnClickListener(this);
    }

    public void onEvent(MemberLetterEvent event) {
        Character targetChar = Character.toLowerCase(event.letter);
        if (contactGroupAdapter.getIndexMap().containsKey(targetChar)) {
            int index = contactGroupAdapter.getIndexMap().get(targetChar);
            if (index > 0 && index < contactGroupAdapter.getItemCount()) {
                layoutManager.scrollToPositionWithOffset(index, 0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leftBtn:
                finish();
                break;
        }
    }
}
