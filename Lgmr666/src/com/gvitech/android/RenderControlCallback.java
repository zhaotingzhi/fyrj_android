package com.gvitech.android;

import android.view.MotionEvent;
import android.view.View;

public abstract interface RenderControlCallback {
	
	  public abstract void onInitialFinished();

	  public abstract void onRest();

	  public abstract void onDestroy();

	  public abstract void onTouch(View paramView, MotionEvent event);

	  public abstract void onCameraFlyFinished(int type);
}
