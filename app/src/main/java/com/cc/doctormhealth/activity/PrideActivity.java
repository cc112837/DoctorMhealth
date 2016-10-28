package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.adapter.EvaluationListAdapter;

public class PrideActivity extends Activity implements View.OnClickListener {
    private ImageView leftBtn;
    private ListView lv_show, cityListView, locationListView;
    private PopupWindow mPopupWindow;
    private EvaluationListAdapter evaluationListAdapter;
    private LinearLayout total_location, layout_left;
    private TextView text_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pride);
        init();
    }

    private void init() {
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        lv_show = (ListView) findViewById(R.id.lv_show);
        evaluationListAdapter = new EvaluationListAdapter(PrideActivity.this, null);
        lv_show.setAdapter(evaluationListAdapter);

    }

    private void showPopupWindow(int width, int height) {
        layout_left = (LinearLayout) LayoutInflater.from(
                PrideActivity.this).inflate(R.layout.popup_category, null);
        locationListView = (ListView) layout_left
                .findViewById(R.id.rootcategory);
        cityListView = (ListView) layout_left.findViewById(R.id.childcategory);
        cityListView.setVisibility(View.INVISIBLE);
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                cityListView.setVisibility(View.VISIBLE);
                cityListView.setAdapter(null);
            }
        });
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();

                text_address.setText("全部");
            }
        });
        mPopupWindow = new PopupWindow(layout_left, width, height * 2 / 3, true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                text_address = (TextView) total_location.findViewById(R.id.text_address);
                text_address.setTextColor(0xff000000);
            }
        });
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAsDropDown(total_location, 5, 1);
        mPopupWindow.update();
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
