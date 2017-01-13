package com.cc.doctormhealth.leanchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.im.v2.messages.AVIMLocationMessage;
import com.avoscloud.leanchatlib.activity.AVChatActivity;
import com.avoscloud.leanchatlib.event.InputBottomBarLocationClickEvent;
import com.avoscloud.leanchatlib.event.LocationItemClickEvent;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.cc.doctormhealth.R;

/**
 * Created by cc112837@163.com on 16/5/18.
 */
public class ChatRoomActivity extends AVChatActivity {
	public static final int LOCATION_REQUEST = 100;
	public static final int QUIT_GROUP_REQUEST = 200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		NotificationUtils.cancelNotification(this);
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chat_ativity_menu, menu);
		alwaysShowMenuItem(menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int menuId = item.getItemId();
		if (menuId == R.id.people) {
			if (null != conversation) {
				Intent intent = new Intent(ChatRoomActivity.this, ConversationDetailActivity.class);
				intent.putExtra(Constants.CONVERSATION_ID, conversation.getConversationId());
				startActivityForResult(intent, QUIT_GROUP_REQUEST);
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
