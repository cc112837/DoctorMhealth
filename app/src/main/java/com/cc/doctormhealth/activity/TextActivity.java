package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cc.doctormhealth.R;

public class TextActivity extends Activity {
    private ImageView leftBtn,iv_1,iv_2;
    private EditText et_name,et_idcard,et_hospital,et_keshi;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        init();
    }

    private void init() {
        leftBtn=(ImageView) findViewById(R.id.leftBtn);
        et_name=(EditText) findViewById(R.id.et_name);
        et_idcard=(EditText) findViewById(R.id.et_idcard);
        et_hospital=(EditText) findViewById(R.id.et_hospital);
        et_keshi=(EditText) findViewById(R.id.et_keshi);
        btn_confirm=(Button) findViewById(R.id.btn_confirm);
        iv_1=(ImageView) findViewById(R.id.iv_1);
        iv_2=(ImageView) findViewById(R.id.iv_2);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
