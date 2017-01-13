package com.cc.doctormhealth.utils;

import android.os.Handler;

import com.cc.doctormhealth.leanchat.model.LeanchatUser;
import com.cc.doctormhealth.model.ConfirmFile;
import com.cc.doctormhealth.model.ForGetpass;
import com.cc.doctormhealth.model.Info;
import com.cc.doctormhealth.model.Money;
import com.cc.doctormhealth.model.Result;
import com.cc.doctormhealth.model.User;
import com.cc.doctormhealth.model.UserEvaluation;
import com.cc.doctormhealth.model.UserInfo;
import com.cc.doctormhealth.model.UserReg;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;


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
            case 11://用户注册
                UserInfo userInfo = (UserInfo) object;
                params.addBodyParameter("phone", userInfo.getPhone());
                params.addBodyParameter("passWord", userInfo.getPass());
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new UserReg(), handler, what));
                break;
            case 12://用户登录
                UserInfo userInf = (UserInfo) object;
                params.addBodyParameter("phone", userInf.getPhone());
                params.addBodyParameter("passWord", userInf.getPass());
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new UserReg(), handler, what));
                break;
            case 13://上传认证头像
                UserInfo user = (UserInfo) object;
                params.addBodyParameter("phone", user.getPhone());
                params.addBodyParameter("imagePath", new File(user.getPass()));
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new Info(), handler, what));
                break;
            case 14://上传认证头像
                UserInfo ser = (UserInfo) object;
                params.addBodyParameter("phone", ser.getPhone());
                params.addBodyParameter("imagePath", new File(ser.getPass()));
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new Info(), handler, what));
                break;
            case 15://医生认证
                User renzheng = (User) object;
                params.addBodyParameter("doctorId", renzheng.getMobile());
                params.addBodyParameter("phone", renzheng.getUsername());
                params.addBodyParameter("userName", renzheng.getTruename());
                params.addBodyParameter("idCard", renzheng.getEmail());
                params.addBodyParameter("hospital", renzheng.getAdr());
                params.addBodyParameter("department", renzheng.getIntro());
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new ConfirmFile(), handler, what));
                break;
            case 16://忘记密码
                params.addBodyParameter("phone", ((UserInfo) object).getPhone());
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new ForGetpass(), handler, what));
                break;
            case 17://修改密码
                params.addBodyParameter("phone", ((UserInfo) object).getPhone());
                params.addBodyParameter("passWord1", ((UserInfo) object).getPass());
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new Result(), handler, what));
                break;
            case 18://我的评价
                params.addBodyParameter("taoId", LeanchatUser.getCurrentUser().getObjectId() + "");
                params.addBodyParameter("status", ((UserInfo) object).getPass());
                params.addBodyParameter("statify", ((UserInfo) object).getPhone());
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new UserEvaluation(), handler, what));
                break;
            case 19:
                params.addBodyParameter("doctorId", LeanchatUser.getCurrentUser().getObjectId());
                sendData(HttpRequest.HttpMethod.POST, url, params, new MyCallBack(new Money(), handler, what));
                break;
        }

    }
}
