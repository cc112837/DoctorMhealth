package com.cc.doctormhealth.utils;

import android.os.Handler;

import com.cc.doctormhealth.model.UserInfo;
import com.cc.doctormhealth.model.UserReg;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**

 */
public class MyHttpUtils extends HttpUtils {
    private static MyHttpUtils httpUtils = new MyHttpUtils();

    public static void sendData(HttpRequest.HttpMethod method, String url, RequestParams params, RequestCallBack requestCallBack) {
        if (method == HttpRequest.HttpMethod.GET) {
            httpUtils.send(method, url, requestCallBack);
        } else if (method == HttpRequest.HttpMethod.POST) {
            httpUtils.send(method, url, params, requestCallBack);
        }
    }

    public static void handData(Handler handler, int what, String url, Object object) {
        RequestParams params = new RequestParams();
        switch (what) {
            case 11:
                UserInfo userInfo = (UserInfo) object;
                params.addBodyParameter("phone", userInfo.getPhone());
                params.addBodyParameter("passWord", userInfo.getPass());
                sendData(HttpRequest.HttpMethod.POST,url,params,new MyCallBack(new UserReg(),handler,what));
                break;
            case 12:
                UserInfo userInf= (UserInfo) object;
                params.addBodyParameter("phone", userInf.getPhone());
                params.addBodyParameter("passWord", userInf.getPass());
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new UserReg(), handler, what));
                break;
        }

    }
}
