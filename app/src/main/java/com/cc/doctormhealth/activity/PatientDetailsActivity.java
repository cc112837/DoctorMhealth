package com.cc.doctormhealth.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.leanchat.activity.ChatRoomActivity;
import com.cc.doctormhealth.model.Info;
import com.cc.doctormhealth.model.AppointUser;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.MyHttpUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cc.doctormhealth.R.id.iv_head;

public class PatientDetailsActivity extends AppCompatActivity {

    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    @Bind(iv_head)
    ImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_aid)
    TextView tvAid;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_bookcontent)
    TextView tvBookcontent;
    @Bind(R.id.tv_message)
    TextView tvMessage;
    @Bind(R.id.tv_start)
    TextView tvStart;
    private AppointUser.DataEntity.AppointDataEntity entityAid;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 25:
                    Info info=(Info) msg.obj;
                    Toast.makeText(PatientDetailsActivity.this,info.getData(),Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_aids);
        ButterKnife.bind(this);
        entityAid = (AppointUser.DataEntity.AppointDataEntity) getIntent().getSerializableExtra("id");
        ImageLoader.getInstance().displayImage(entityAid.getUserImage(),ivHead, PhotoUtils.avatarImageOption);
        tvName.setText(entityAid.getName());
        tvSex.setText(entityAid.getSex());
        tvAge.setText(entityAid.getAge());
        tvContent.setText(entityAid.getCaseness());
        tvBookcontent.setText(entityAid.getIllness());
        if("1".equals(entityAid.getAppointStatu())){
            tvStart.setEnabled(true);
            tvStart.setBackgroundResource(R.drawable.textview);
        }else if("0".equals(entityAid.getAppointStatu())){
            tvStart.setEnabled(false);
            tvStart.setText("未报道");
            tvStart.setBackgroundResource(R.drawable.textview3);
        }else{
            tvStart.setEnabled(false);
            tvStart.setText("已完成");
            tvStart.setBackgroundResource(R.drawable.textview3);
        }
    }

    @OnClick({R.id.leftBtn, R.id.tv_message,R.id.tv_start,R.id.tv_aid})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftBtn:
                finish();
                break;
            case R.id.tv_aid:
                if ("0".equals(entityAid.getCheckCase())) {
                    Intent intent = new Intent(PatientDetailsActivity.this, CaseHisManagerActivity.class);
                    intent.putExtra("id", entityAid.getAppointId());
                    startActivity(intent);
                } else {
                    new AlertDialog.Builder(PatientDetailsActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("暂无病历可以查询！")//设置显示的内容
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    dialog.dismiss();
                                }

                            }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            dialog.dismiss();
                        }
                    }).show();//在按键响应事件中显示此对话框
                }
                break;
            case R.id.tv_message:
                ChatManager chatManager = ChatManager.getInstance();
                chatManager.fetchConversationWithUserId(entityAid.getUserId(),
                        new AVIMConversationCreatedCallback() {
                            @Override
                            public void done(AVIMConversation conversation, AVIMException e) {
                                if (e != null) {
                                    Toast.makeText(PatientDetailsActivity.this, e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(PatientDetailsActivity.this,
                                            ChatRoomActivity.class);
                                    intent.putExtra(com.avoscloud.leanchatlib.utils.Constants.CONVERSATION_ID,
                                            conversation.getConversationId());
                                    startActivity(intent);}
                            }
                        });
                break;
            case R.id.tv_start:
                String url= Constants.SERVER_URL+"PatientAppointStatuServlet";
                User user=new User();
                user.setUsername(entityAid.getAppointId()+"");
                MyHttpUtils.handData(handler,25,url,user);
                break;
        }
    }
}
