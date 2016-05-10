package com.cc.doctormhealth.leanchat.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MHealth.R;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avoscloud.chat.event.ConversationItemClickEvent;
import com.avoscloud.chat.service.CacheService;
import com.avoscloud.chat.service.ConversationManager;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.controller.ConversationHelper;
import com.avoscloud.leanchatlib.controller.MessageHelper;
import com.avoscloud.leanchatlib.model.ConversationType;
import com.avoscloud.leanchatlib.model.LeanchatUser;
import com.avoscloud.leanchatlib.model.Room;
import com.avoscloud.leanchatlib.utils.PhotoUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.greenrobot.event.EventBus;

/**
 * Created by wli on 15/10/8.
 */
public class ConversationListAdapter extends ArrayAdapter<Room> {

	public ConversationListAdapter(Context context) {
		super(context, 0);
		this.ctx = context;
	}

	Context ctx;

	// public void setDataList(List<T> datas) {
	// dataList.clear();
	// if (null != datas) {
	// dataList.addAll(datas);
	// }
	// }

	// public void addDataList(List<T> datas) {
	// dataList.addAll(0, datas);
	// }
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder vh = new ViewHolder();
		final Room room = getItem(position);
		convertView = LayoutInflater.from(ctx).inflate(
				R.layout.conversation_item, null);

		vh.recentAvatarView = (ImageView) convertView
				.findViewById(R.id.iv_recent_avatar);
		vh.recentNameView = (TextView) convertView
				.findViewById(R.id.recent_time_text);
		vh.recentMsgView = (TextView) convertView
				.findViewById(R.id.recent_msg_text);
		vh.recentTimeView = (TextView) convertView
				.findViewById(R.id.recent_teim_text);
		vh.recentUnreadView = (TextView) convertView
				.findViewById(R.id.recent_unread);
		AVIMConversation conversation = room.getConversation();
		if (null != conversation) {
			if (ConversationHelper.typeOfConversation(conversation) == ConversationType.Single) {
				LeanchatUser user = (LeanchatUser) CacheService
						.lookupUser(ConversationHelper
								.otherIdOfConversation(conversation));
				if (null != user) {
					ImageLoader.getInstance().displayImage(user.getAvatarUrl(),
							vh.recentAvatarView, PhotoUtils.avatarImageOptions);
				}
			} else {
				vh.recentAvatarView.setImageBitmap(ConversationManager
						.getConversationIcon(conversation));
			}
			vh.recentNameView.setText(ConversationHelper
					.nameOfConversation(conversation));

			int num = room.getUnreadCount();
			if (num > 0) {
				vh.recentUnreadView.setVisibility(View.VISIBLE);
				vh.recentUnreadView.setText(num + "");
			} else {
				vh.recentUnreadView.setVisibility(View.GONE);
			}

			if (room.getLastMessage() != null) {
				Date date = new Date(room.getLastMessage().getTimestamp());
				SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
				vh.recentTimeView.setText(format.format(date));

				// TODO 此处并不一定是 AVIMTypedMessage
				vh.recentMsgView
						.setText(MessageHelper
								.outlineOfMsg((AVIMTypedMessage) room
										.getLastMessage()));
			}

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ConversationItemClickEvent itemClickEvent = new ConversationItemClickEvent();
					itemClickEvent.conversationId = room.getConversationId();
					EventBus.getDefault().post(itemClickEvent);
				}
			});
			convertView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					Builder builder = new Builder(ctx);
					builder.setMessage("确认删除？");
					builder.setTitle("确认删除？ʾ");
					builder.setPositiveButton("是",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									ChatManager
											.getInstance()
											.getRoomsTable()
											.deleteRoom(
													room.getConversationId());
								}
							});
					builder.setNegativeButton("否",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
					builder.create().show();

					return false;
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		ImageView recentAvatarView;
		TextView recentNameView;
		TextView recentMsgView;
		TextView recentTimeView;
		TextView recentUnreadView;
	}
}
