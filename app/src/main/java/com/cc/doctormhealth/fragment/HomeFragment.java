package com.cc.doctormhealth.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avoscloud.leanchatlib.event.ImTypeMessageEvent;
import com.avoscloud.leanchatlib.model.Room;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.CaptureActivity;
import com.cc.doctormhealth.activity.ManagerActivity;
import com.cc.doctormhealth.activity.MessageActivity;
import com.cc.doctormhealth.activity.NewsActivity;
import com.cc.doctormhealth.activity.NewsDetailActivity;
import com.cc.doctormhealth.activity.OrderAidActivity;
import com.cc.doctormhealth.activity.ScanresultActivity;
import com.cc.doctormhealth.adapter.NewsItemAdapter;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.leanchat.service.ConversationManager;
import com.cc.doctormhealth.model.BannerItem;
import com.cc.doctormhealth.model.NewsYang;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.utils.MyHttpUtils;
import com.cc.doctormhealth.view.LocalImageHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {
    private ConversationManager conversationManager = ConversationManager.getInstance();
    ConvenientBanner convenientBanner;
    @Bind(R.id.lv_show)
    ListView lvShow;
    LinearLayout ll_1, ll_2, ll_3, ll_4;
    List<NewsYang.DataEntity>  titleList=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 21:
                    NewsYang newsYang=(NewsYang) msg.obj;
                    if(newsYang!=null){
                        titleList.addAll(newsYang.getData());
                        newsAdapter.notifyDataSetChanged();
                    }
                    lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                            intent.putExtra("content", titleList.get(position-1).getMedicalId()+"");
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };
    @Bind(R.id.iv_see)
    ImageView ivSee;
    @Bind(R.id.iv_meg)
    ImageView ivMeg;
    @Bind(R.id.tv_count)
    TextView tvCount;
    private ArrayList<BannerItem> localImages = new ArrayList<>();
    private NewsItemAdapter newsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        String url= Constants.SERVER_URL+"MedicalArticleServlet";
        User user=new User();
        user.setUsername(1+"");
        user.setAdr(1+"");
        MyHttpUtils.handData(handler, 21, url, user);
        init(view);
        return view;
    }

    private void init(View view) {
        View headview = LayoutInflater.from(getContext()).inflate(R.layout.home_header, null);
        ll_1 = (LinearLayout) headview.findViewById(R.id.ll_1);
        ll_2 = (LinearLayout) headview.findViewById(R.id.ll_2);
        ll_3 = (LinearLayout) headview.findViewById(R.id.ll_3);
        ll_4 = (LinearLayout) headview.findViewById(R.id.ll_4);
        ll_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //患者预约
                Intent intent = new Intent(getActivity(), OrderAidActivity.class);
                startActivity(intent);
            }
        });
        ll_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //患者管理
                Intent intent = new Intent(getActivity(), ManagerActivity.class);
                startActivity(intent);
            }
        });
        ll_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //医疗资讯
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
        ll_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //患者咨询
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
        convenientBanner = (ConvenientBanner) headview.findViewById(R.id.convenientBanner);
        for (int i = 0; i < 2; i++) {
            BannerItem bannerItem = new BannerItem();
            bannerItem.setTitle("1");
            bannerItem.setUrl("http://ss.bdimg.com/static/superman/img/logo/bd_logo1_31bdc765.png");
            bannerItem.setId(i + "");
            localImages.add(bannerItem);
        }
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.dots_gray, R.mipmap.dot_white})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        lvShow.addHeaderView(headview);

        newsAdapter = new NewsItemAdapter(getActivity(), titleList);
        lvShow.setAdapter(newsAdapter);
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


    public void updateCount() {
        conversationManager.findAndCacheRooms(new Room.MultiRoomsCallback() {
            @Override
            public void done(List<Room> roomList, AVException exception) {
                if (exception == null) {
                    int count = 0;
                    for (Room room : roomList)
                        count += room.getUnreadCount();
                    if (count > 0) {
                        tvCount.setVisibility(View.VISIBLE);
                        tvCount.setText("" + count);
                    } else
                        tvCount.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public void onEvent(ImTypeMessageEvent event) {
        updateCount();
    }

    @OnClick({R.id.iv_see, R.id.iv_meg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_see:

                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.iv_meg:
                Intent intent1 = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
