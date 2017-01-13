package com.cc.doctormhealth.leanchat.friends;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.SaveCallback;
import com.avoscloud.leanchatlib.utils.LogUtils;
import com.cc.doctormhealth.leanchat.model.LeanchatUser;
import com.cc.doctormhealth.leanchat.service.PushManager;
import com.cc.doctormhealth.leanchat.util.SimpleNetTask;
import com.cc.doctormhealth.leanchat.util.Utils;
import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.R;

import java.util.List;

/**
 * Created by lzw on 14-9-27.
 */
public  class AddRequestManager {
  private static AddRequestManager addRequestManager;

  /**
   * 用户端未读的邀请消息的数量
   */
  private int unreadAddRequestsCount = 0;

  public static synchronized AddRequestManager getInstance() {
    if (addRequestManager == null) {
      addRequestManager = new AddRequestManager();
    }
    return addRequestManager;
  }

  public AddRequestManager() {}

  /**
   * 是否有未读的消息
   */
  public boolean hasUnreadRequests() {
    return unreadAddRequestsCount > 0;
  }

  /**
   * 推送过来时自增
   */
  public void unreadRequestsIncrement() {
    ++ unreadAddRequestsCount;
  }

  /**
   * 从 server 获取未读消息的数量
   */
  public void countUnreadRequests(final CountCallback countCallback) {
    AVQuery<com.cc.doctormhealth.leanchat.friends.AddRequest> addRequestAVQuery = AVObject.getQuery(com.cc.doctormhealth.leanchat.friends.AddRequest.class);
    addRequestAVQuery.setCachePolicy(AVQuery.CachePolicy.NETWORK_ONLY);
    addRequestAVQuery.whereEqualTo(com.cc.doctormhealth.leanchat.friends.AddRequest.TO_USER, LeanchatUser.getCurrentUser());
    addRequestAVQuery.whereEqualTo(com.cc.doctormhealth.leanchat.friends.AddRequest.IS_READ, false);
    addRequestAVQuery.countInBackground(new CountCallback() {
      @Override
      public void done(int i, AVException e) {
        if (null != countCallback) {
          unreadAddRequestsCount = i;
          countCallback.done(i, e);
        }
      }
    });
  }

  /**
   * 标记消息为已读，标记完后会刷新未读消息数量
   */
  public void markAddRequestsRead(List<com.cc.doctormhealth.leanchat.friends.AddRequest> addRequestList) {
    if (addRequestList != null) {
      for (com.cc.doctormhealth.leanchat.friends.AddRequest request : addRequestList) {
        request.put(com.cc.doctormhealth.leanchat.friends.AddRequest.IS_READ, true);
      }
      AVObject.saveAllInBackground(addRequestList, new SaveCallback() {
        @Override
        public void done(AVException e) {
          if (e == null) {
            countUnreadRequests(null);
          }
        }
      });
    }
  }

  public void findAddRequests(int skip, int limit, FindCallback findCallback) {
    LeanchatUser user = LeanchatUser.getCurrentUser();
    AVQuery<com.cc.doctormhealth.leanchat.friends.AddRequest> q = AVObject.getQuery(com.cc.doctormhealth.leanchat.friends.AddRequest.class);
    q.include(com.cc.doctormhealth.leanchat.friends.AddRequest.FROM_USER);
    q.skip(skip);
    q.limit(limit);
    q.whereEqualTo(com.cc.doctormhealth.leanchat.friends.AddRequest.TO_USER, user);
    q.orderByDescending(AVObject.CREATED_AT);
    q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
    q.findInBackground(findCallback);
  }

  public void agreeAddRequest(final com.cc.doctormhealth.leanchat.friends.AddRequest addRequest, final SaveCallback saveCallback) {
    addFriend(addRequest.getFromUser().getObjectId(), new SaveCallback() {
      @Override
      public void done(AVException e) {
        if (e != null) {
          if (e.getCode() == AVException.DUPLICATE_VALUE) {
            addRequest.setStatus(com.cc.doctormhealth.leanchat.friends.AddRequest.STATUS_DONE);
            addRequest.saveInBackground(saveCallback);
          } else {
            saveCallback.done(e);
          }
        } else {
          addRequest.setStatus(com.cc.doctormhealth.leanchat.friends.AddRequest.STATUS_DONE);
          addRequest.saveInBackground(saveCallback);
        }
      }
    });
  }

  public static void addFriend(String friendId, final SaveCallback saveCallback) {
    LeanchatUser user = LeanchatUser.getCurrentUser();
    user.followInBackground(friendId, new FollowCallback() {
      @Override
      public void done(AVObject object, AVException e) {
        if (saveCallback != null) {
          saveCallback.done(e);
        }
      }
    });
  }

  private void createAddRequest(LeanchatUser toUser) throws Exception {
    LeanchatUser curUser = LeanchatUser.getCurrentUser();
    AVQuery<com.cc.doctormhealth.leanchat.friends.AddRequest> q = AVObject.getQuery(com.cc.doctormhealth.leanchat.friends.AddRequest.class);
    q.whereEqualTo(com.cc.doctormhealth.leanchat.friends.AddRequest.FROM_USER, curUser);
    q.whereEqualTo(com.cc.doctormhealth.leanchat.friends.AddRequest.TO_USER, toUser);
    q.whereEqualTo(com.cc.doctormhealth.leanchat.friends.AddRequest.STATUS, com.cc.doctormhealth.leanchat.friends.AddRequest.STATUS_WAIT);
    int count = 0;
    try {
      count = q.count();
    } catch (AVException e) {
      LogUtils.logException(e);
      if (e.getCode() == AVException.OBJECT_NOT_FOUND) {
        count = 0;
      } else {
        throw e;
      }
    }
    if (count > 0) {
      // 抛出异常，然后提示用户
      throw new IllegalStateException(MyApplication.ctx.getString(R.string.contact_alreadyCreateAddRequest));
    } else {
      com.cc.doctormhealth.leanchat.friends.AddRequest add = new com.cc.doctormhealth.leanchat.friends.AddRequest();
      add.setFromUser(curUser);
      add.setToUser(toUser);
      add.setStatus(com.cc.doctormhealth.leanchat.friends.AddRequest.STATUS_WAIT);
      add.setIsRead(false);
      add.save();
    }
  }

  public void createAddRequestInBackground(Context ctx, final LeanchatUser user) {
    new SimpleNetTask(ctx) {
      @Override
      protected void doInBack() throws Exception {
        createAddRequest(user);
      }

      @Override
      protected void onSucceed() {
        PushManager.getInstance().pushMessage(user.getObjectId(), ctx.getString(R.string.push_add_request),
                ctx.getString(R.string.invitation_action));
        Utils.toast(R.string.contact_sendRequestSucceed);
      }
    }.execute();
  }
}
