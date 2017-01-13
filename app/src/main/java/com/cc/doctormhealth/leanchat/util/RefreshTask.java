package com.cc.doctormhealth.leanchat.util;

import android.content.Context;

import com.cc.doctormhealth.leanchat.util.SimpleNetTask;

/**
 * Created by lzw on 15/2/27.
 */
public abstract class RefreshTask extends SimpleNetTask {
  private com.cc.doctormhealth.leanchat.util.Refreshable refreshable;

  public RefreshTask(Context cxt, com.cc.doctormhealth.leanchat.util.Refreshable refreshable) {
    super(cxt);
    this.refreshable = refreshable;
  }

  @Override
  protected void onSucceed() {
    this.refreshable.refresh();
  }
}
