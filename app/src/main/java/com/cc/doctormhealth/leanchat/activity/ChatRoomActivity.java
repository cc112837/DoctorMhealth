package com.cc.doctormhealth.leanchat.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMLocationMessage;
import com.avoscloud.leanchatlib.activity.AVBaseActivity;
import com.avoscloud.leanchatlib.activity.ChatFragment;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.ConversationHelper;
import com.avoscloud.leanchatlib.controller.MessageHelper;
import com.avoscloud.leanchatlib.event.EmptyEvent;
import com.avoscloud.leanchatlib.event.ImageItemClickEvent;
import com.avoscloud.leanchatlib.event.LocationItemClickEvent;
import com.avoscloud.leanchatlib.model.ConversationType;
import com.avoscloud.leanchatlib.utils.Constants;
import com.avoscloud.leanchatlib.utils.LogUtils;
import com.avoscloud.leanchatlib.utils.NotificationUtils;
import com.cc.doctormhealth.R;

/**
 * Created by lzw on 15/4/24.
 */
public class ChatRoomActivity extends AVBaseActivity {
	public static final int LOCATION_REQUEST = 100;
	public static final int QUIT_GROUP_REQUEST = 200;

	protected ChatFragment chatFragment;
	protected AVIMConversation conversation;
	protected ImageView image;
//	protected ImageView right;
	protected TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		image = (ImageView) findViewById(R.id.leftBtn);
//		right = (ImageView) findViewById(R.id.rightBtn);
		title = (TextView) findViewById(R.id.titleViewOfChatRoom);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		chatFragment = (ChatFragment) getFragmentManager().findFragmentById(
				R.id.fragment_chat);
		initByIntent(getIntent());
//		right.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (ConversationHelper.typeOfConversation(conversation) == ConversationType.Single) {
//					List<String> Ids = new ArrayList<String>();
//					Ids.addAll(conversation.getMembers());
//					Ids.remove(AVUser.getCurrentUser().getObjectId());
//					if (null != Ids && Ids.size() == 1) {
//						LeanchatUser user = CacheService.lookupUser(Ids.get(0));
//						Intent i = new Intent(ChatRoomActivity.this, FriendActivity.class);
//						i.putExtra("userId", user.getObjectId());
//						i.putExtra("username", user.getUsername());
//						startActivity(i);
//					}
//				}
//			}
//		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		initByIntent(intent);
	}

	private void initByIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		if (null != extras) {
			if (extras.containsKey(Constants.MEMBER_ID)) {
				getConversation(extras.getString(Constants.MEMBER_ID));
			} else if (extras.containsKey(Constants.CONVERSATION_ID)) {
				String conversationId = extras.getString(Constants.CONVERSATION_ID);
				updateConversation(AVIMClient.getInstance(
						ChatManager.getInstance().getSelfId()).getConversation(
						conversationId));
			} else {
			}
		}
	}

	protected void initActionBar(String title) {
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			if (title != null) {
				actionBar.setTitle(title);
			}
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setDisplayHomeAsUpEnabled(true);
		} else {
			LogUtils
					.i("action bar is null, so no title, please set an ActionBar style for activity");
		}
	}

	public void onEvent(EmptyEvent emptyEvent) {
	}

	protected void updateConversation(AVIMConversation con) {
		if (null != con) {
			this.conversation = con;
			chatFragment.setConversation(con);
			chatFragment.showUserName(ConversationHelper
					.typeOfConversation(con) != ConversationType.Single);
			initActionBar(ConversationHelper.titleOfConversation(con));

			if (ConversationHelper.typeOfConversation(conversation) == ConversationType.Group)
				title.setText(conversation.getName());
			else
				title.setText(ConversationHelper.titleOfConversation(conversation));
		}
	}

	/**
	 * 获取 conversation，为了避免重复的创建，此处先 query 是否已经存在只包含该 member 的 conversation
	 * 如果存在，则直接赋值给 ChatFragment，否者创建后再赋值
	 */
	private void getConversation(final String memberId) {
		ChatManager.getInstance().fetchConversationWithUserId(memberId,
				new AVIMConversationCreatedCallback() {

					@Override
					public void done(AVIMConversation arg0, AVIMException e) {
						// TODO Auto-generated method stub
						if (e == null) {
							ChatManager.getInstance().getRoomsTable()
									.insertRoom(arg0.getConversationId());
							updateConversation(arg0);
						}
					}
				});
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
		return super.onCreateOptionsMenu(menu);
	}

	// @Override
	// public boolean onMenuItemSelected(int featureId, MenuItem item) {
	// int menuId = item.getItemId();
	// if (menuId == R.id.people) {
	// if (null != conversation) {
	// Intent intent = new Intent(ChatRoomActivity.this,
	// ConversationDetailActivity.class);
	// intent.putExtra(Constants.CONVERSATION_ID,
	// conversation.getConversationId());
	// startActivityForResult(intent, QUIT_GROUP_REQUEST);
	// }
	// }
	// return super.onMenuItemSelected(featureId, item);
	// }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case QUIT_GROUP_REQUEST:
				finish();
				break;
			}
		}
	}

	public void onEvent(LocationItemClickEvent event) {
		if (null != event && null != event.message
				&& event.message instanceof AVIMLocationMessage) {
			AVIMLocationMessage locationMessage = (AVIMLocationMessage) event.message;
		}
	}

	public void onEvent(ImageItemClickEvent event) {
		if (null != event && null != event.message
				&& event.message instanceof AVIMImageMessage) {
			AVIMImageMessage imageMessage = (AVIMImageMessage) event.message;
			ImageBrowserActivity.go(this, MessageHelper.getFilePath(imageMessage),
					imageMessage.getFileUrl());
		}
	}
}
