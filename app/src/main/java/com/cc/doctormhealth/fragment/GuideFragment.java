package com.cc.doctormhealth.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;
/**
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 导航页的主页面
*/
public class GuideFragment extends android.support.v4.app.Fragment {


    public GuideFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_guide, container, false);

        initView(ret);

        return ret;
    }

    private void initView(View ret) {
        List<Integer> images = new ArrayList<>();

        images.add(R.mipmap.welcome_1);
        images.add(R.mipmap.welcome_2);
        images.add(R.mipmap.welcome_3);

        Bundle arguments = getArguments();

        int id = arguments.getInt("id");

        ImageView imageView = ((ImageView) ret.findViewById(R.id.guide_imageView));

        TextView goToSee = ((TextView) ret.findViewById(R.id.goToSee));


        switch (id){
            case 0:
                imageView.setImageResource(images.get(id));
                break;
            case 1:
                imageView.setImageResource(images.get(id));
                break;
            case 2:
                imageView.setImageResource(images.get(id));
                goToSee.setVisibility(View.VISIBLE);
                goToSee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);

                        startActivity(intent);

                        getActivity().finish();
                    }
                });
                break;


        }
    }


}
