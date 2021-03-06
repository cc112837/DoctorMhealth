package com.cc.doctormhealth.leanchat.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avoscloud.leanchatlib.viewholder.CommonViewHolder;
import com.cc.doctormhealth.R;
import com.cc.doctormhealth.leanchat.adapter.ContactsAdapter;
import com.cc.doctormhealth.leanchat.event.ContactItemClickEvent;
import com.cc.doctormhealth.leanchat.event.ContactItemLongClickEvent;
import com.cc.doctormhealth.leanchat.pinyin.PinyinHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.greenrobot.event.EventBus;

/**
 * Created by wli on 15/11/24.
 */
public class ContactItemHolder extends CommonViewHolder<ContactsAdapter.ContactItem> {

  TextView alpha;
  TextView nameView;
  ImageView avatarView;

  public ContactsAdapter.ContactItem contactItem;

  public ContactItemHolder(Context context, ViewGroup root) {
    super(context, root, R.layout.common_user_item);
    initView();
  }

  public void initView() {
    alpha = (TextView)itemView.findViewById(R.id.alpha);
    nameView = (TextView)itemView.findViewById(R.id.tv_friend_name);
    avatarView = (ImageView)itemView.findViewById(R.id.img_friend_avatar);
    itemView.setVisibility(View.GONE);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        EventBus.getDefault().post(new ContactItemClickEvent(contactItem.user.getObjectId()));
        return ;
      }
    });

    itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        EventBus.getDefault().post(new ContactItemLongClickEvent(contactItem.user.getObjectId()));
        return true;
      }
    });
  }

  @Override
  public void bindData(ContactsAdapter.ContactItem memberItem) {
    contactItem = memberItem;
    alpha.setVisibility(memberItem.initialVisible ? View.VISIBLE : View.GONE);
    alpha.setText(String.valueOf(Character.toUpperCase(PinyinHelper.getShortPinyin(memberItem.sortContent).charAt(0))));
    ImageLoader.getInstance().displayImage(memberItem.user.getAvatarUrl(),
      avatarView, com.avoscloud.leanchatlib.utils.PhotoUtils.avatarImageOptions);
    nameView.setText((String)memberItem.user.get("realName"));
    itemView.setVisibility(View.VISIBLE);
  }

  public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<ContactItemHolder>() {
    @Override
    public ContactItemHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
      return new ContactItemHolder(parent.getContext(), parent);
    }
  };
}
