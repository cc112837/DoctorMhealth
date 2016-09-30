package com.cc.doctormhealth.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;


/**
 */
public class MyCallBack extends RequestCallBack<String> {
    private Object object;
    private Handler handler;
    private int what;

    public MyCallBack(Object object, Handler handler, int what) {
        this.object = object;
        this.handler = handler;
        this.what = what;
    }

    @Override
    public void onSuccess(ResponseInfo<String> responseInfo) {
        Log.e("成功",responseInfo.result);
        object = new Gson().fromJson(responseInfo.result, object.getClass());
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = object;
        handler.sendMessage(msg);
    }

    @Override
    public void onFailure(HttpException error, String msg) {
        Log.i("httputils", "失败");
    }
}
