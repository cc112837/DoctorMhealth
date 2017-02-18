package com.cc.doctormhealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.view.NetworkImageHolderView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageScannerActivity extends AppCompatActivity {

    @Bind(R.id.leftBtn)
    ImageView leftBtn;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scanner);
        ButterKnife.bind(this);
        List<String> imageList =  getIntent().getStringArrayListExtra("name");
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, imageList).setPageIndicator(new int[]{R.mipmap.dot_white, R.mipmap.common_msg_tips}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @OnClick(R.id.leftBtn)
    public void onClick() {
        finish();
    }
}
