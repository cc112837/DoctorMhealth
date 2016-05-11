package com.cc.doctormhealth.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.activity.ChangePwdActivity;
import com.cc.doctormhealth.activity.LoginActivity;
import com.cc.doctormhealth.activity.NotiNewsActivity;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.leanchat.service.PushManager;
import com.cc.doctormhealth.leanchat.util.PathUtils;
import com.cc.doctormhealth.leanchat.util.PhotoUtils;
import com.cc.doctormhealth.utils.CacheUtils;
import com.cc.doctormhealth.utils.LogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    RelativeLayout logout, account, noti_news, about;
    TextView username;
    ImageView headImage;
    String dateTime;
    private AlertDialog avatarDialog;
    private ChatManager chatManager = ChatManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        account = (RelativeLayout) view.findViewById(R.id.account);
        chatManager = ChatManager.getInstance();
        noti_news = (RelativeLayout) view.findViewById(R.id.noti_news);
        about = (RelativeLayout) view.findViewById(R.id.about);
        logout = (RelativeLayout) view.findViewById(R.id.logout);
        username = (TextView) view.findViewById(R.id.username);
        username.setText(Constants.USER_NAME);
        headImage = (ImageView) view.findViewById(R.id.headView);
        LeanchatUser curUser = AVUser.getCurrentUser(LeanchatUser.class);
        String avatarUrl = curUser.getAvatarUrl();
        if(avatarUrl==null){
            try {
                JSONObject object = new JSONObject( curUser.toString());
                JSONObject serverData = object.getJSONObject("serverData");
                JSONObject avatar = serverData.getJSONObject("avatar");
                avatarUrl = avatar.getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
            }}

        ImageLoader.getInstance().displayImage(avatarUrl, headImage,
                com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOptions);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AvatarDialog();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chatManager.closeWithCallback(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                    }
                });
                PushManager.getInstance().unsubscribeCurrentUserChannel();
                AVUser.logOut();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
//                System.exit(0);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChangePwdActivity.class);
                getActivity().startActivity(intent);

            }
        });
        noti_news.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotiNewsActivity.class);
                startActivity(intent);

            }
        });
        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
    public void AvatarDialog() {

        avatarDialog = new AlertDialog.Builder(getContext()).create();
        avatarDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.my_headicon, null);
        avatarDialog.show();
        avatarDialog.setContentView(v);
        avatarDialog.getWindow().setGravity(Gravity.CENTER);
        TextView albumPic = (TextView) v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView) v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                avatarDialog.dismiss();
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                showAvatarFromAlbum();
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";
                showAvatarFromCamera();
            }
        });
    }

    private void showAvatarFromCamera() {
        File f = new File(CacheUtils.getCacheDirectory(getContext(), true, "icon") + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(camera, 1);
    }

    private void showAvatarFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                String files = CacheUtils.getCacheDirectory(getContext(), true, "icon") + dateTime;
                File file = new File(files);
                if (file.exists() && file.length() > 0) {
                    Uri uri = Uri.fromFile(file);
                    startPhotoZoom(uri);
                } else {
                }
                break;
            case 2:
                if (data == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case 3:
                if (data != null) {
                    LogUtil.e("data3","执行了");
                    final String path = saveCropAvatar(data);
                    LeanchatUser user = LeanchatUser.getCurrentUser();
                    user.saveAvatar(path, new SaveCallback() {
                        @Override
                        public void done(AVException arg0) {
                            if (arg0 == null) {
                                LeanchatUser curUser = LeanchatUser.getCurrentUser(LeanchatUser.class);
                                String avatarUrl = curUser.getAvatarUrl();
                                ImageLoader
                                        .getInstance()
                                        .displayImage(
                                                avatarUrl,
                                                headImage,
                                                com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOptions);
                            }

                        }
                    });
                }
                break;
            default:
                break;
        }

    }

    private String saveCropAvatar(Intent data) {
        Bundle extras = data.getExtras();
        String path = null;
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            if (bitmap != null) {
                bitmap = PhotoUtils.toRoundCorner(bitmap, 10);
                path = PathUtils.getAvatarCropPath();
                PhotoUtils.saveBitmap(path, bitmap);
                if (bitmap != null && bitmap.isRecycled() == false) {
                    bitmap.recycle();
                }
            }
        }
        return path;
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent,3);

    }


}
