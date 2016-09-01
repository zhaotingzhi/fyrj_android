package com.tianhedaoyun.lgmr.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.tianhedaoyun.lgmr.activity.ModelMain;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class Panel extends LinearLayout {

	public interface PanelClosedEvent {
		void onPanelClosed(View panel);
	}

	public interface PanelOpenedEvent {
		void onPanelOpened(View panel);
	}

	/** Handle的宽度，与Panel等高 */
	private static int HANDLE_HEIGHT = 60;
	/** 每次自动展开/收缩的范围 */
	private final static int MOVE_HEIGHT = 400;
	private Button btnHandle;
	private LinearLayout panelContainer;
	private int mTopMargin = 0;
	private Context mContext;
	private PanelClosedEvent panelClosedEvent = null;
	private PanelOpenedEvent panelOpenedEvent = null;
	LayoutParams lp ;

	/**
	 * otherView自动布局以适应Panel展开/收缩的空间变化
	 * 
	 * @author GV
	 *
	 */
	@SuppressLint("NewApi")
	public Panel(Context context, View otherView, int width, int height) {
		super(context);
		this.mContext = context;

		// 改变Panel附近组件的属性
		LayoutParams otherLP = (LayoutParams) otherView.getLayoutParams();
		otherLP.weight = 1;// 支持压挤
		otherView.setLayoutParams(otherLP);

		// 设置Panel本身的属性
		lp= new LayoutParams(width, height);
		lp.bottomMargin = -lp.height + HANDLE_HEIGHT +250;// Panel的Container在屏幕不可视区域，Handle在可视区域
		mTopMargin = Math.abs(lp.bottomMargin);
		this.setLayoutParams(lp);
		this.setBackgroundColor(Color.TRANSPARENT);
		this.setOrientation(LinearLayout.VERTICAL);

		// 设置Container的属性
		panelContainer = new LinearLayout(context);
		panelContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		this.addView(panelContainer);
	}

	public boolean isOpen() {
		LayoutParams lp = (LayoutParams) Panel.this.getLayoutParams();
		if (lp.bottomMargin >= 0) {
			return true;
		}
		return false;
	}
	
	public void xclose(){
		lp.bottomMargin =-lp.height+0;
	}
	public void xopen(){
		lp.bottomMargin = -lp.height + HANDLE_HEIGHT +250;
	}

	public void close(ImageView iv, RelativeLayout r_connect) {
		if (r_connect.getVisibility() != View.GONE) {
			r_connect.setVisibility(View.GONE);
		}
		android.widget.RelativeLayout.LayoutParams param = (android.widget.RelativeLayout.LayoutParams) iv
				.getLayoutParams();
		param.topMargin = 1585;
		iv.setLayoutParams(param);
		
		new AsynMove().execute(new Integer[] { -MOVE_HEIGHT });// 负数收缩
	}

	public void open(ModelMain modelMain, ImageView iv) {
		android.widget.RelativeLayout.LayoutParams param = (android.widget.RelativeLayout.LayoutParams) iv
				.getLayoutParams();
		param.topMargin = 1245;
		iv.setLayoutParams(param);
		new AsynMove().execute(new Integer[] { MOVE_HEIGHT });// 正数展开

	}

	public void openOrClose(ModelMain modelMain, ImageView iv, RelativeLayout r_connect) {
		LayoutParams lp = (LayoutParams) Panel.this.getLayoutParams();
		if (lp.bottomMargin < 0) {// CLOSE的状态
			android.widget.RelativeLayout.LayoutParams param = (android.widget.RelativeLayout.LayoutParams) iv
					.getLayoutParams();
			param.topMargin = 1245;
			iv.setLayoutParams(param);
			new AsynMove().execute(new Integer[] { MOVE_HEIGHT });// 正数展开
		} else if (lp.bottomMargin >= 0) {// OPEN的状态
			if (r_connect.getVisibility() != View.GONE) {
				r_connect.setVisibility(View.GONE);
			}
			android.widget.RelativeLayout.LayoutParams param = (android.widget.RelativeLayout.LayoutParams) iv
					.getLayoutParams();
			param.topMargin = 1585;
			iv.setLayoutParams(param);
			new AsynMove().execute(new Integer[] { -MOVE_HEIGHT });// 负数收缩
		}
	}

	/**
	 * 定义收缩时的回调函数
	 * 
	 * @param event
	 */
	public void setPanelClosedEvent(PanelClosedEvent event) {
		this.panelClosedEvent = event;
	}

	/**
	 * 定义展开时的回调函数
	 * 
	 * @param event
	 */
	public void setPanelOpenedEvent(PanelOpenedEvent event) {
		this.panelOpenedEvent = event;
	}

	/**
	 * 把View放在Panel的Container
	 * 
	 * @param v
	 */
	public void fillPanelContainer(View v) {
		panelContainer.addView(v);
	}

	/**
	 * 异步移动Panel
	 * 
	 * @author hellogv
	 */
	class AsynMove extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			int times;
			if (mTopMargin % Math.abs(params[0]) == 0) {// 整除
				times = mTopMargin / Math.abs(params[0]);
			} else {
				// 有余数
				times = mTopMargin / Math.abs(params[0]) + 1;
			}
			for (int i = 0; i < times; i++) {
				publishProgress(params);
				try {
					Thread.sleep(Math.abs(params[0]));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... params) {
			LayoutParams lp = (LayoutParams) Panel.this.getLayoutParams();
			if (params[0] < 0)
				lp.bottomMargin = Math.max(lp.bottomMargin + params[0], (-mTopMargin));
			else
				lp.bottomMargin = Math.min(lp.bottomMargin + params[0], 0);

			if (lp.bottomMargin == 0 && panelOpenedEvent != null) {// 展开之后
				panelOpenedEvent.onPanelOpened(Panel.this);// 调用OPEN回调函数
			} else if (lp.bottomMargin == -(mTopMargin) && panelClosedEvent != null) {// 收缩之后
				panelClosedEvent.onPanelClosed(Panel.this);// 调用CLOSE回调函数
			}
			Panel.this.setLayoutParams(lp);
		}
	}

}
