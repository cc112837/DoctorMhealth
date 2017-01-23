package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.zxingdemo.CreateQRImageTest;

public class BarCodeActivity extends AppCompatActivity {
    private ImageView qrImgImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);
        String contentString = LeanchatUser.getCurrentUser().getObjectId();
        CreateQRImageTest createQRImageTest = new CreateQRImageTest(qrImgImageView);
        createQRImageTest.createQRImage(contentString);


        //返回箭头
        back();
    }

    private void back() {
        // TODO Auto-generated method stub
        ImageView imageback = (ImageView) findViewById(R.id.leftBtn);

        imageback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
