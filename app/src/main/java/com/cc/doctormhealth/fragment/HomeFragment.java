package com.cc.doctormhealth.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.CaptureActivity;
import com.cc.doctormhealth.activity.ScanresultActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {
    @Bind(R.id.iv_see)
    ImageView ivSee;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override

    public void onResume() {

        super.onResume();
        convenientBanner.startTurning(2000);
    }


    // 停止自动翻页

    @Override

    public void onPause() {

        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();

    }
    @OnClick(R.id.iv_see)
    public void onClick() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 200) {
                String num = data.getStringExtra("result");
                Intent intent = null;
                if (isValidURI(num)) {
                    intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(num);
                    intent.setData(content_url);
                } else {
                    intent = new Intent(getActivity(),
                            ScanresultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("num", num);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            } else if (resultCode == 0) {
            }
        } else {
            return;
        }
    }

    public static boolean isValidURI(String uri) {
        if (uri == null || uri.indexOf(' ') >= 0 || uri.indexOf('\n') >= 0) {
            return false;
        }
        String scheme = Uri.parse(uri).getScheme();
        if (scheme == null) {
            return false;
        }

        // Look for period in a domain but followed by at least a two-char TLD
        // Forget strings that don't have a valid-looking protocol
        int period = uri.indexOf('.');
        if (period >= uri.length() - 2) {
            return false;
        }
        int colon = uri.indexOf(':');
        if (period < 0 && colon < 0) {
            return false;
        }
        if (colon >= 0) {
            if (period < 0 || period > colon) {
                // colon ends the protocol
                for (int i = 0; i < colon; i++) {
                    char c = uri.charAt(i);
                    if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                        return false;
                    }
                }
            } else {
                // colon starts the port; crudely look for at least two numbers
                if (colon >= uri.length() - 2) {
                    return false;
                }
                for (int i = colon + 1; i < colon + 3; i++) {
                    char c = uri.charAt(i);
                    if (c < '0' || c > '9') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
