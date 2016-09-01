package com.tianhedaoyun.lgmr.view;

import java.util.HashMap;

import com.tianhedaoyun.lgmr.R;
import com.tianhedaoyun.lgmr.adapter.DragListAdapter;
import com.tianhedaoyun.lgmr.adapter.ItemInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;



/**
 * 有隐藏
 *
 */
public class DragListView extends ListView {

	private WindowManager windowManager;// windows窗口控制类
	private WindowManager.LayoutParams windowParams;// 用于控制拖拽项的显示的参数

	private int scaledTouchSlop;// 判断滑动的一个距离,scroll的时候会用到(24)

	private ImageView dragImageView;// 被拖拽的项(item)，其实就是一个ImageView
	private int startPosition =-1;// 手指拖动项原始在列表中的位置
	private int dragPosition;// 手指点击准备拖动的时候,当前拖动项在列表中的位置.
	private int lastPosition;// 手指点击准备拖动的时候,当前拖动项在列表中的位置.
	
	private ViewGroup dragItemView = null;//拖动时隐藏的view

	private int dragPoint;// 在当前数据项中的位置
	private int dragOffset;// 当前视图和屏幕的距离(这里只使用了y方向上)

	private int upScrollBounce;// 拖动的时候，开始向上滚动的边界
	private int downScrollBounce;// 拖动的时候，开始向下滚动的边界

	private final static int step = 1;// ListView 滑动步伐.

	private int current_Step;// 当前步伐.

	private boolean isLock;// 是否上锁.
	
	private ItemInfo mDragItemInfo;
	private boolean isMoving = false;
	private boolean isDragItemMoving = false;
	
	private int mItemVerticalSpacing = 0;
	
	private boolean bHasGetSapcing = false;

	public static final int MSG_DRAG_STOP = 0x1001;
	public static final int MSG_DRAG_MOVE = 0x1002;
	private static final int ANIMATION_DURATION = 200;
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_DRAG_STOP:
				break;
			case MSG_DRAG_MOVE:
				break;
			default:
				break;
			}
		};
	};
	
	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public DragListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mDragItemInfo = new ItemInfo();
		init();
	}
	
	private void init(){
		windowManager = (WindowManager) getContext().getSystemService("window");
	}

	private void getSpacing(){
		bHasGetSapcing = true;
		
		upScrollBounce = getHeight() / 3;// 取得向上滚动的边际，大概为该控件的1/3
		downScrollBounce = getHeight() * 2 / 3;// 取得向下滚动的边际，大概为该控件的2/3
		
		int[] tempLocation0 = new int[2];
		int[] tempLocation1 = new int[2];
		
		ViewGroup itemView0 = (ViewGroup) getChildAt(0);//第一行
		ViewGroup itemView1  = (ViewGroup) getChildAt(1);//第二行
		
		if(itemView0 != null){
			itemView0.getLocationOnScreen(tempLocation0);
		}else{
			return;
		}
		
		if(itemView1 != null){
			itemView1.getLocationOnScreen(tempLocation1);
			mItemVerticalSpacing = Math.abs(tempLocation1[1] - tempLocation0[1]);
		}else{
			return;
		}
	}
	int nx=-1,ny=-1;
	/***
	 * touch事件拦截 在这里我进行相应拦截，
	 */

	public Animation getScaleAnimation(){
		Animation scaleAnimation= new   
			     ScaleAnimation(0.0f,0.0f,0.0f,0.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setFillAfter(true);
//		scaleAnimation.setDuration(300);	
		return scaleAnimation;
	}
	
//	private void hideDropItem(){
//		final DragListAdapter adapter = (DragListAdapter)this.getAdapter();
//		adapter.showDropItem(false);
//	}
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 按下
		if (ev.getAction() == MotionEvent.ACTION_CANCEL&&!isMoving) {
			int xx = (int) ev.getX()-nx;
			int yy = (int) ev.getY()-ny;
	
			 return false;
		}
		if (ev.getAction() == MotionEvent.ACTION_DOWN&&!isMoving) {
			int x = (int) ev.getX();// 获取相对与ListView的x坐标
			int y = (int) ev.getY();// 获取相应与ListView的y坐标
			startPosition = pointToPosition(x, y);
			// 无效不进行处理
			if (startPosition == AdapterView.INVALID_POSITION) {
				return super.onInterceptTouchEvent(ev);
			}
			
			// 获取当前位置的视图(可见状态)
			ViewGroup dragger = (ViewGroup) getChildAt(startPosition
					- getFirstVisiblePosition());

			DragListAdapter adapter = (DragListAdapter) getAdapter();
			
			mDragItemInfo.obj = adapter.getItem(startPosition
					- getFirstVisiblePosition());
			View draggerCont = dragger.findViewById(R.id.item_cont);
			if(draggerCont != null ){
				nx=x;ny=y;	
				 System.out.println(x+","+y);
			}
			return false;
		}



		return super.onInterceptTouchEvent(ev);
	}


	/**
	 * 触摸事件处理
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		

		return super.onTouchEvent(ev);
	}

	private boolean isSameDragDirection = true;
	private int lastFlag = -1; //-1,0 == down,1== up
	
	private int mFirstVisiblePosition, mLastVisiblePosition;
	private int mCurFirstVisiblePosition, mCurLastVisiblePosition;
	
	private boolean isNormal = true;
	private int turnUpPosition, turnDownPosition;
	
	private void onChangeCopy(int last, int current) {
		DragListAdapter adapter = (DragListAdapter) getAdapter();
		if (last != current) {
			//adapter.exchangeCopy(last, current);
			Log.i("wanggang", "onChange");
		}

	}

	private void onDrop(int x,int y){
		final DragListAdapter adapter = (DragListAdapter) getAdapter();
		adapter.notifyDataSetChanged();	
	}
	
	

}