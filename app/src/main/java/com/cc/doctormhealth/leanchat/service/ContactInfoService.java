package com.cc.doctormhealth.leanchat.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.cc.doctormhealth.leanchat.model.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：DoctorMhealth
 * 类描述：
 * 创建人：吴聪聪
 * 邮箱：cc112837@163.com
 * 创建时间：2017/1/24 9:15
 * 修改人：Administrator
 * 修改时间：2017/1/24 9:15
 * 修改备注：
 */

public class ContactInfoService {
    private Context context;

    public ContactInfoService(Context context) {
        this.context = context;
    }

    public List<ContactBean> getContactList() {

        List<ContactBean> mContactBeanList = new ArrayList<>();
        ContactBean mContactBean = null;
        ContentResolver mContentResolver = context.getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = null;
        try {
            String[] projection = {ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME, "sort_key"};
            cursor = mContentResolver.query(uri, projection, null, null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (null != cursor && cursor.moveToNext()) {
            int nameFieldColumnIndex = cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int idCol = cursor.getInt(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            int sort_key_index = cursor.getColumnIndex("sort_key");
            mContactBean = new ContactBean();

            // 取得联系人ID
            Cursor phone = mContentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                    new String[] { Integer.toString(idCol) }, null);//
            // 再类ContactsContract.CommonDataKinds.Phone中根据查询相应id联系人的所有电话；

            // 取得电话号码(可能存在多个号码)
            while ( phone.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("_id"));

                String title = cursor.getString(cursor.getColumnIndex("display_name"));//获取联系人姓名
                String strPhoneNumber = phone
                        .getString(phone
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String firstHeadLetter = cursor.getString(sort_key_index);//这个字段保存了每个联系人首字的拼音的首字母
                mContactBean.setTitle(title);
                mContactBean.setFirstHeadLetter(firstHeadLetter);
                mContactBean.setPhoneNum(strPhoneNumber);
            }
            phone.close();
            if (mContactBean.getTitle() != null && mContactBean.getPhoneNum() != null) {
                mContactBeanList.add(mContactBean);
            }

        }
        cursor.close();
        return mContactBeanList;
    }
}