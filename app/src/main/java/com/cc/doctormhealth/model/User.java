/**
 * 
 */
package com.cc.doctormhealth.model;

import android.graphics.Bitmap;
import android.widget.ImageView;


/**
 * @author MZH
 *
 */
public class User {
	public String nickname;
	public String username;
	public String truename;
	public String email;
	public String headimg;
	public String intro;
	public String mobile;
	public String sex;
	public String adr;
	public Bitmap bitmap;
	
	public User() {
		super();
	}
	
	
	public void showHead(ImageView imageView) {
		if (bitmap!=null) {
			imageView.setImageBitmap(bitmap);
		}
	}
}
