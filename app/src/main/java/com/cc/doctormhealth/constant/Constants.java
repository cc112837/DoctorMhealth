package com.cc.doctormhealth.constant;


import com.cc.doctormhealth.utils.Util;

public class Constants {
	public final static boolean IS_DEBUG = true;
	public final static String SERVER_HOST = "123.57.191.21";//"10.109.32.145";//
	public final static String SERVER_URL = "http://"+SERVER_HOST+":9090/plugins/xmppservice/";
	public static String SERVER_NAME = "123.57.192.21";//"user-3nrk8rvtjm";//
	public final static int SERVER_PORT = 5222;
	public final static String PATH =  Util.getInstance().getExtPath()+"/xmpp";
	public final static String SAVE_IMG_PATH = PATH + "/images";//设置保存图片文件的路径
	public final static String SAVE_SOUND_PATH = PATH + "/sounds";//设置声音文件的路径
	
	public final static String SHARED_PREFERENCES = "openfile";
	public final static String LOGIN_CHECK = "check";
	public final static String LOGIN_ACCOUNT = "u1";
	public final static String LOGIN_PWD = "123";
	
	
	public static String USER_NAME = "";
	public static String icon = "";
	public static String PWD = "";
//	public static User loginUser;


}
