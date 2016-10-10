package com.cc.doctormhealth.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.model.ConfirmFile;
import com.cc.doctormhealth.model.Info;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.model.UserInfo;
import com.cc.doctormhealth.utils.CacheUtils;
import com.cc.doctormhealth.utils.MyHttpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class TextActivity extends Activity implements View.OnClickListener {
    private ImageView leftBtn, iv_1, iv_2;
    private EditText et_name, et_idcard, et_hospital, et_keshi;
    private Button btn_confirm;
    String dateTime, path;
    Bitmap bitmap1;
    private AlertDialog avatarDialog;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        init();
    }

    private void init() {
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        et_name = (EditText) findViewById(R.id.et_name);
        et_idcard = (EditText) findViewById(R.id.et_idcard);
        et_hospital = (EditText) findViewById(R.id.et_hospital);
        et_keshi = (EditText) findViewById(R.id.et_keshi);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        iv_1 = (ImageView) findViewById(R.id.iv_1);
        iv_2 = (ImageView) findViewById(R.id.iv_2);
        iv_2.setOnClickListener(this);
        iv_1.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 15:
                    ConfirmFile confirmFile=(ConfirmFile) msg.obj;
                    if (confirmFile.getStatus().equals("1")){
                        Toast.makeText(TextActivity.this,"资质提交成功，请等待",Toast.LENGTH_LONG).show();
                        Intent intent =new Intent(TextActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(TextActivity.this,"请检查信息是否完整",Toast.LENGTH_LONG).show();

                    }
                    break;
                case 13:
                    Info info = (Info) msg.obj;
                    if (info.getStatus().equals("1")) {
                        iv_1.setImageBitmap(bitmap1);
                    }
                    break;
                case 14:
                    Info inf = (Info) msg.obj;
                    if (inf.getStatus().equals("1")) {
                        iv_2.setImageBitmap(bitmap1);
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_1:
                flag = 13;
                AvatarDialog();
                break;
            case R.id.iv_2:
                flag = 14;
                AvatarDialog();
                break;
            case R.id.leftBtn:
                finish();
                break;
            case R.id.btn_confirm:
                String name = et_name.getText().toString();
                String idcard = et_idcard.getText().toString();
                String hospital = et_hospital.getText().toString();
                String keshi = et_keshi.getText().toString();
                if (name == null || idcard == null || hospital == null || keshi == null) {
                    Toast.makeText(TextActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
                } else {
                    User user = new User();
                    user.setMobile(LeanchatUser
                            .getCurrentUser().getObjectId()+"");
                    user.setUsername(MyApplication.sharedPreferences.getString(Constants.LOGIN_ACCOUNT, ""));
                    user.setTruename(name);
                    user.setEmail(idcard);
                    user.setAdr(hospital);
                    user.setIntro(keshi);
                    String url=Constants.SERVER_URL+"MhealthDoctorCheckServlet";
                    MyHttpUtils.handData(handler, 15, url, user);
                }
                break;
        }
    }

    private void AvatarDialog() {
        avatarDialog = new AlertDialog.Builder(TextActivity.this).create();
        avatarDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(TextActivity.this).inflate(R.layout.my_headicon, null);
        avatarDialog.show();
        avatarDialog.setContentView(v);
        avatarDialog.getWindow().setGravity(Gravity.CENTER);
        RadioButton albumPic = (RadioButton) v.findViewById(R.id.album_pic);
        RadioButton cameraPic = (RadioButton) v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                avatarDialog.dismiss();
                Date date1 = new Date(System.currentTimeMillis());
                dateTime = date1.getTime() + "";
                showAvatarFromAlbum();//从图库选择
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
                Date date = new Date(System.currentTimeMillis());
                dateTime = date.getTime() + "";
                showAvatarFromCamera();//从相机选择
            }
        });
    }

    private void showAvatarFromCamera() {
        File f = new File(CacheUtils.getCacheDirectory(TextActivity.this, true, "icon") + dateTime);
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
                String files = CacheUtils.getCacheDirectory(TextActivity.this, true, "icon") + dateTime;
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
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        bitmap1 = extras.getParcelable("data");
                        path = saveToSdCard(bitmap1);
                        String uri = null;
                        if (flag == 13) {
                            uri = Constants.SERVER_URL + "DoctorCheckUploadServlet";//服务端的头像地址
                        }
                        if (flag == 14) {
                            uri = Constants.SERVER_URL + "MhealthDoctorCheckUploadServlet";
                        }
                        UserInfo user = new UserInfo();
                        user.setPhone(MyApplication.sharedPreferences.getString(Constants.LOGIN_ACCOUNT, ""));
                        user.setPass(path);
                        MyHttpUtils.handData(handler, flag, uri, user);
                    }


                }

                break;

            default:
                break;
        }

    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(TextActivity.this, true, "icon") + dateTime + "_12";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // LogUtils.i(TAG, file.getAbsolutePath());
        return file.getAbsolutePath();
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
        startActivityForResult(intent, 3);

    }
}
