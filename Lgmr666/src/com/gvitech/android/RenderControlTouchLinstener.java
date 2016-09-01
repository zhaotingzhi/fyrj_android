package com.gvitech.android;



import com.tianhedaoyun.lgmr.activity.ModelMain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class RenderControlTouchLinstener implements OnTouchListener {
	private int	touchCount = 0;
	private int[] touchInfo = new int[10 * 10];
	private RenderControlCallback callback;
	private float downX;
	private float downY;
	
	public RenderControlTouchLinstener(RenderControlCallback cb){
		this.callback = cb;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean onTouch(View v, MotionEvent event) {
		touchCount = event.getPointerCount();
		
		int nMotion = 0;		
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				nMotion = 0x2;
			    this.downX = event.getX();
			    this.downY = event.getY();
			    ModelMain.coordinate.setVisibility(View.GONE);
			    ModelMain.distance.setVisibility(View.GONE);
			    ModelMain.move.setVisibility(View.GONE);
			    ModelMain.function.setVisibility(View.GONE);
				break;
			case MotionEvent.ACTION_POINTER_1_DOWN:
				nMotion = 0x2;
				break;
			case MotionEvent.ACTION_POINTER_2_DOWN:
				nMotion = 0x2;
				break;
			case MotionEvent.ACTION_POINTER_3_DOWN:
				nMotion = 0x2;
				break;
	
			case MotionEvent.ACTION_CANCEL:
				nMotion = 0;
				break;
				
			case MotionEvent.ACTION_UP:
				/*if(ModelMain.img_location.getHeight()>200){return	false;}*/
				nMotion = 0x4;
				if ((this.callback != null) && 
				    (Math.abs(this.downX - event.getX()) < 8.0F) && 
				    (Math.abs(this.downY - event.getY()) < 8.0F)) {
				    this.callback.onTouch(v, event);
				}
				 ModelMain.coordinate.setVisibility(View.VISIBLE);
				 ModelMain.distance.setVisibility(View.VISIBLE);
				 ModelMain.move.setVisibility(View.VISIBLE);
				 ModelMain.function.setVisibility(View.VISIBLE);
				break;
			case MotionEvent.ACTION_POINTER_1_UP:
				nMotion = 0x4;
				break;
			case MotionEvent.ACTION_POINTER_2_UP:
				nMotion = 0x4;
				break;
			case MotionEvent.ACTION_POINTER_3_UP:
				nMotion = 0x4;
				break;
				
			case MotionEvent.ACTION_MOVE:
				nMotion = 0x1;
				ModelMain.btn_modelInfo.setVisibility(8);
				ModelMain.btn_showModel.setVisibility(8);
				ModelMain.btn_addModel.setVisibility(8);
				/*if(ModelMain.img_location.getHeight()>100){
				double x =ModelMain.img_location.getX()+ModelMain.img_location.getWidth()/2;
				double y =ModelMain.img_location.getY()+ModelMain.img_location.getHeight()/2;
				ModelMain.img_location.setRotation(getRotation(event.getX()-x,event.getY()-y));
				}*/
				break;
				
			default:
				return	false;
		}
		
		if (nMotion == 0x2 || nMotion == 0x4) {
			int nActionIndex = event.getActionIndex();
			for (int i = 0; i < touchCount; i++){
				int nID = event.getPointerId(i);
				
				touchInfo[i * 10 + 0] = (int) event.getX(i);
				touchInfo[i * 10 + 1] = (int) event.getY(i);
				touchInfo[i * 10 + 2] = 0;
				touchInfo[i * 10 + 3] = nID;
				touchInfo[i * 10 + 4] = (i == nActionIndex)? nMotion : 0x8;//MotionEvent.ACTION_POINTER_2_DOWN;;
				touchInfo[i * 10 + 5] = 0;
				touchInfo[i * 10 + 6] = (int) event.getEventTime();
				touchInfo[i * 10 + 7] = 0;
				touchInfo[i * 10 + 8] = 0;
				touchInfo[i * 10 + 9] = 0;
			}
		}
		else{
			for (int i = 0; i < touchCount; i++){
				int nID = event.getPointerId(i);
				
				touchInfo[i * 10 + 0] = (int) event.getX(i);
				touchInfo[i * 10 + 1] = (int) event.getY(i);
				touchInfo[i * 10 + 2] = 0;
				touchInfo[i * 10 + 3] = nID;
				touchInfo[i * 10 + 4] = nMotion;//MotionEvent.ACTION_POINTER_2_DOWN;;
				touchInfo[i * 10 + 5] = 0;
				touchInfo[i * 10 + 6] = (int) event.getEventTime();
				touchInfo[i * 10 + 7] = 0;
				touchInfo[i * 10 + 8] = 0;
				touchInfo[i * 10 + 9] = 0;
			}
		}
		
		RenderControl.get().nativeTouchEvent(touchCount, touchInfo);
		
		return true;
	}
    float getRotation(double x,double y){
    	
    	double cos = Math.atan(y/x);
    	float dgr = (float)Math.toDegrees(cos)+90;
    	  System.out.println(x+","+y+","+dgr);
    	if(x>0&&y>0){return dgr;}
    	if(x<0&&y>0){return 180+dgr;}
    	if(x<0&&y<0){return 180+dgr;}
    	if(x>0&&y<0){return dgr;}
    	return dgr;
    }
}
