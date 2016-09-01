package com.gvitech.android;

import java.util.Calendar;

import com.google.vrtoolkit.cardboard.HeadTransformExt;
import com.google.vrtoolkit.cardboard.sensors.HeadTracker;
import com.gvitech.android.EnumValue.*;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RenderControl {

    private native boolean initializeT(Surface surface, AssetManager assertManager);
    private native boolean initialize2T(Surface surface, AssetManager assertManager, String wkt);
    public native boolean uninitialize();
    public native boolean resetView(Surface surface);
    public native boolean pauseRendering(boolean dumpMemory);
    public native boolean resumeRendering();	
    public native boolean reset(boolean isPlanarTerrain);
    public native void nativeTouchEvent(int touchCount, int[] touchPosArray);
    public native boolean enterWalkMode(String roadSdb, String password);
    
    public IObjectManager objectManager = new IObjectManager();
	public ICamera camera = new ICamera();
	public ICacheManager cacheManager = new ICacheManager();
	public IViewport viewport = new IViewport();
	public IExportManager exportManager = new IExportManager();
	
    private AssetManager assertManager;
    private SurfaceView surfaceView;
    private RenderControlCallback callback;
    Context context;
    private String strWkt;
    
    //陀螺仪控制
    private int mSensorTimerInterval = 40;		//接收陀螺仪消息的频率（单位：毫秒）
    private boolean mSendSensorUpdate = false;
    private HeadTransformExt mHeadTransform = null;
	private HeadTracker mHeadTracker = null;
	private float[] mTmpEuler = null;
	private float[] mTmpLook = null;
	private float[] mTmpRight = null;
	private float[] mTmpUp = null;
	private float[] mTmpMat = null;
	private Handler mSensorTimerHandler = new Handler();
	private Runnable mSensorTimerRunnable = new Runnable(){
		public void run(){
			if(mTmpEuler == null)
				mTmpEuler = new float[3];		
			if(mTmpLook == null)
				mTmpLook = new float[3];	
			if(mTmpRight == null)
				mTmpRight = new float[3];	
			if(mTmpUp == null)
				mTmpUp = new float[3];	
			if(mTmpMat == null)
				mTmpMat = new float[16];	
			mHeadTracker.getLastHeadView(mHeadTransform.getHeadView(), 0);
			//mHeadTransform.getEulerAngles(mTmpEuler, 0);
			
			
			//String str = String.format("%.0f, %.0f, %.0f", roll, pitch, yaw);
			//System.out.println(str);
			
			//String str;
			float tmp;
			
			//mTmpLook[0] = mTmpMat[4];
			//mTmpLook[1] = mTmpMat[5];
			//mTmpLook[2] = mTmpMat[6];
			mHeadTransform.getRightVector(mTmpLook, 0);
			mTmpLook[0] = -mTmpLook[0];
			mTmpLook[1] = -mTmpLook[1];
			mTmpLook[2] = -mTmpLook[2];
			//str = String.format("%.2f, %.2f, %.2f; ", mTmpLook[0], mTmpLook[1], mTmpLook[2]);
			
			//mTmpRight[0] = mTmpMat[0];
			//mTmpRight[1] = mTmpMat[1];
			//mTmpRight[2] = mTmpMat[2];
			mHeadTransform.getForwardVector(mTmpRight, 0);
			//str += String.format("%.2f, %.2f, %.2f; ", mTmpRight[0], mTmpRight[1], mTmpRight[2]);
			
			//mTmpUp[0] = mTmpMat[8];
			//mTmpUp[1] = mTmpMat[9];
			//mTmpUp[2] = mTmpMat[10];
			mHeadTransform.getUpVector(mTmpUp, 0);
			//str += String.format("%.2f, %.2f, %.2f", mTmpUp[0], mTmpUp[1], mTmpUp[2]);
			
			tmp = mTmpRight[1]; mTmpRight[1] = mTmpLook[0]; mTmpLook[0] = tmp;
			tmp = mTmpRight[2]; mTmpRight[2] = mTmpUp[0];   mTmpUp[0] = tmp;
			tmp = mTmpLook[2];  mTmpLook[2]  = mTmpUp[1];   mTmpUp[1] = tmp;
			
			double q0, q1, q2, q3;
		    double s;
		    double[] tq = new double[4];
		    int    i, j;
		    
		    // Use tq to store the largest trace
		    tq[0] = 1 + mTmpRight[0]+mTmpLook[1]+mTmpUp[2];
		    tq[1] = 1 + mTmpRight[0]-mTmpLook[1]-mTmpUp[2];
		    tq[2] = 1 - mTmpRight[0]+mTmpLook[1]-mTmpUp[2];
		    tq[3] = 1 - mTmpRight[0]-mTmpLook[1]+mTmpUp[2];

		    // Find the maximum (could also use stacked if's later)
		    j = 0;
		    for(i=1;i<4;i++) j = (tq[i]>tq[j])? i : j;

		    // check the diagonal
		    if (j==0)
		    {
		        /* perform instant calculation */
		        q0 = tq[0];
		        q1 = mTmpLook[2]-mTmpUp[1]; 
		        q2 = mTmpUp[0]-mTmpRight[2]; 
		        q3 = mTmpRight[1]-mTmpLook[0]; 
		    }
		    else if (j==1)
		    {
		        q0 = mTmpLook[2]-mTmpUp[1]; 
		        q1 = tq[1];
		        q2 = mTmpRight[1]+mTmpLook[0]; 
		        q3 = mTmpUp[0]+mTmpRight[2]; 
		    }
		    else if (j==2)
		    {
		        q0 = mTmpUp[0]-mTmpRight[2]; 
		        q1 = mTmpRight[1]+mTmpLook[0]; 
		        q2 = tq[2];
		        q3 = mTmpLook[2]+mTmpUp[1]; 
		    }
		    else /* if (j==3) */
		    {
		        q0 = mTmpRight[1]-mTmpLook[0]; 
		        q1 = mTmpUp[0]+mTmpRight[2]; 
		        q2 = mTmpLook[2]+mTmpUp[1]; 
		        q3 = tq[3];
		    }

		    s = Math.sqrt(0.25/tq[j]);
		    q0 *= s;
		    q1 *= s;
		    q2 *= s;
		    q3 *= s;

		    double[][] Tt = new double[3][3];
		    Tt[0][0] = q0 * q0 + q1 * q1 - q2 * q2 - q3 * q3 ;
		    Tt[0][1] = 2.0 * (q1 * q2 - q0 * q3);
		    Tt[0][2] = 2.0 * (q1 * q3 + q0 * q2);
		    Tt[1][0] = 2.0 * (q1 * q2 + q0 * q3);
		    Tt[1][1] = q0 * q0 - q1 * q1 + q2 * q2 - q3 * q3 ;
		    Tt[1][2] = 2.0 * (q2 * q3 - q0 * q1);
		    Tt[2][0] = 2.0 * (q1 * q3 - q0 * q2);
		    Tt[2][1] = 2.0 * (q2 * q3 + q0 * q1);
		    Tt[2][2] = q0 * q0 - q1 * q1 - q2 * q2 + q3 * q3 ;

		    double dRoll = Math.asin(-Tt[2][0]);
		    double dPitch = Math.atan(Tt[2][1] / Tt[2][2]);
		    double dYaw   = Math.atan(Tt[1][0] / Tt[0][0]);

		    if (Tt[2][2] < 0)
		    {
		        if (dPitch < 0)
		        {
		            dPitch += Math.PI;
		        }
		        else
		        {
		            dPitch -= Math.PI;
		        }
		    }
		    if (Tt[0][0] < 0)
		    {
		        if (Tt[1][0] > 0)
		        {
		            dYaw += Math.PI;
		        }
		        else
		        {
		            dYaw -= Math.PI;
		        }
		    }
		    dYaw = -dYaw;
		    if (dYaw < 0)
		    {
		        dYaw += (Math.PI*2.0);
		    }

		    double yaw = dYaw/Math.PI*180-90;
		    double pitch = dPitch/Math.PI*180-90;
		    double roll = dRoll/Math.PI*180;
		    			
			//str = String.format("%.2f, %.2f, %.2f; ", yaw, pitch, roll);
			//System.out.println(str);

			sensorChanged(roll, pitch, yaw);	
			mSensorTimerHandler.postDelayed(this, mSensorTimerInterval);
		}
	};
    
    private static RenderControl inst = null;
    private RenderControl(){    	
    }
    public static RenderControl get(){
		if(inst == null)
			inst = new RenderControl();
		return inst;
	}
    
	public void initialize(Context ctx, SurfaceView sv, AssetManager am, RenderControlCallback cb) {
		this.assertManager = am;
		this.surfaceView = sv;
		this.callback = cb;
		this.context = ctx;
		this.exportManager.setContext(ctx);
		this.surfaceView.setOnTouchListener(new RenderControlTouchLinstener(callback));
		this.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
			public void surfaceCreated(SurfaceHolder holder){
				//设置缓存
				{	
					String strCacheDir = context.getExternalCacheDir().getAbsolutePath();						
					RenderControl.get().cacheManager.setFileCachePath(strCacheDir);					
				}				
				initializeT(holder.getSurface(), assertManager);				
				callback.onInitialFinished();
			}
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				resetView(holder.getSurface());				
				callback.onRest();
			}
			public void surfaceDestroyed(SurfaceHolder holder){
				uninitialize();
				//android.os.Process.killProcess(android.os.Process.myPid());
				callback.onDestroy();
			}
		});		
	}
	
	public void initialize2(Context ctx, SurfaceView sv, AssetManager am, RenderControlCallback cb, String wkt) {
		this.assertManager = am;
		this.surfaceView = sv;
		this.callback = cb;
		this.context = ctx;
		this.exportManager.setContext(ctx);
		this.strWkt = wkt;
		this.surfaceView.setOnTouchListener(new RenderControlTouchLinstener(callback));
		this.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
			public void surfaceCreated(SurfaceHolder holder){
				//设置缓存
				{	
					String strCacheDir = context.getExternalCacheDir().getAbsolutePath();						
					RenderControl.get().cacheManager.setFileCachePath(strCacheDir);					
				}				
				initialize2T(holder.getSurface(), assertManager, strWkt);				
				callback.onInitialFinished();
			}
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				resetView(holder.getSurface());				
				callback.onRest();
			}
			public void surfaceDestroyed(SurfaceHolder holder){
				uninitialize();
				callback.onDestroy();
			}
		});		
	}
	
	private native int getInteractModeT();
	
	public gviInteractMode getInteractMode() {
		int n = getInteractModeT();
		gviInteractMode r = gviInteractMode.gviInteractNormal;
		for(gviInteractMode e : gviInteractMode.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}
	
	public native void setInteractMode(gviInteractMode newVal);
	
	private native int getMouseSelectModeT();
	
	public gviMouseSelectMode getMouseSelectMode(){
		int n = getMouseSelectModeT();
		gviMouseSelectMode r = gviMouseSelectMode.gviMouseSelectClick;
		for(gviMouseSelectMode e : gviMouseSelectMode.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}
	
	public native void setMouseSelectMode(gviMouseSelectMode newVal);

	public native int getMouseSelectObjectMask();	
	public native void setMouseSelectObjectMask(int newVal);
	
	private native int getMeasurementModeT();
	
	public gviMeasurementMode getMeasurementMode() {
		int n = getMeasurementModeT();
		gviMeasurementMode r = gviMeasurementMode.gviMeasureAerialDistance;
		for(gviMeasurementMode e : gviMeasurementMode.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}
	
	public native void setMeasurementMode(gviMeasurementMode newVal);
	
	private native void sensorChanged(double roll, double pitch, double yaw);
	
	//开启陀螺仪控制
	public boolean startSensorControl(){
		
		if(!mSendSensorUpdate) {
			 mSendSensorUpdate = true;	 		
			 
			 if(mHeadTracker == null){
				 mHeadTransform = new HeadTransformExt();
			     mHeadTracker = new HeadTracker(context);
			 }			 
		     mHeadTracker.startTracking();
		     mSensorTimerHandler.postDelayed(mSensorTimerRunnable, mSensorTimerInterval);
		}	
	     
		return true;
	}
	
	//停止陀螺仪控制
	public void stopSensorControl()	{
		mSendSensorUpdate = false;
		mHeadTracker.stopTracking();
		mSensorTimerHandler.removeCallbacks(mSensorTimerRunnable);
	}
	
	
	public native double internalTest(String p1, String p2);
	
	
//	/*!< 焦距。 */
//    public native float getStereoFusionDistance();   
//    public native void setStereoFusionDistance();   
//    
//    /*!< 两只眼睛的间距。 */
//    public native float getStereoEyeSeparation();  
//    public native void setStereoEyeSeparation();  
//    
//    /*!< 离屏幕的距离。 */
//    public native float getStereoScreenDistance();  
//    public native void setStereoScreenDistance();  
    
    
	public native float getOculusInterpupillaryDistance();
	public native void setOculusInterpupillaryDistance(float newVal);

	public native float getOculusProjectionCenterOffset();
	public native void setOculusProjectionCenterOffset(float newVal);
	
	
	public String getSignatureCode(){
		return android.os.Build.SERIAL;
	}
	
	private native int setLicenseInfoT(String licFile, String signature, int year, int month, int day);	

	public int SetLicenseFile(String strFile) {
		Calendar now  = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		int day = now.get(Calendar.DAY_OF_MONTH);		
		int nRet = this.setLicenseInfoT(strFile, this.getSignatureCode(), year, month, day);
		return nRet;
	}
	
	public void cameraFlyFinished(int type){		
		this.callback.onCameraFlyFinished(type);
	}

	
	static {
		try
		{
			System.loadLibrary("CityMaker");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

