package com.gvitech.android;

import com.gvitech.android.EnumValue.*;

public class ICamera {
	
	public native double getFlyTime();
	public native void setFlyTime(double newVal);
	
	private native void setCameraT(double x, double y, double z, double heading, double tilt, double roll, gviSetCameraFlags flags);
	
	public void setCamera(IVector3 position, IEulerAngle angle, gviSetCameraFlags flags) {
		this.setCameraT(position.getX(), position.getY(), position.getZ(), 
				angle.getHeading(), angle.getTilt(), angle.getRoll(), flags);
	}
		
	/**
	 * @example:
	 * double[] cc = RenderControl.get().camera.getCamera();
	 * 返回的数组元素：x, y, z, heading, tilt, roll
	 */
    public native double[] getCamera();
    
    
    private native void lookAtT(double x, double y, double z, double distance, double heading, double tilt, double roll);
	
    public void lookAt(IVector3 position, double distance, IEulerAngle angle) {
		this.lookAtT(position.getX(), position.getY(), position.getZ(),
				distance,
				angle.getHeading(), angle.getTilt(), angle.getRoll());
	}
    
	
	private native double[] getAimingAnglesT(double x1, double y1, double z1, double x2, double y2, double z2);
	
	/**
	 * @example:
	 	IVector3 position1 = new IVector3(0, 0, 0);
		IVector3 position2 = new IVector3(100, 100, 100);
		IEulerAngle ang = RenderControl.get().camera.getAimingAngles(position1, position2);
	 */
	public IEulerAngle getAimingAngles(IVector3 position1, IVector3 position2) {
		double[] ar = this.getAimingAnglesT(position1.getX(), position1.getY(), position1.getZ(),
				position2.getX(), position2.getY(), position2.getZ());
		
		IEulerAngle r = null;
		if(ar.length == 3){		
			double heading = ar[0];
			double tilt = ar[1];
			double roll = ar[2];
			r = new IEulerAngle(heading, tilt ,roll);
		}
		
		return r;
	}
    
	   
    private native int getFlyModeT();
    public gviFlyMode getFlyMode(){
    	return gviFlyMode.values()[getFlyModeT()];
    }
        
    public native void setFlyMode(gviFlyMode newVal);
    
    private native int getWalkModeT();
    public gviWalkMode getWalkMode(){
    	return gviWalkMode.values()[getWalkModeT()];
    }
        
    public native void setWalkMode(gviWalkMode newVal);
    
    
    public native int getWalkSpeed();    
    public native void setWalkSpeed(float newVal);
    
    private native int getCollisionDetectionModeT();
    public gviCollisionDetectionMode getCollisionDetectionMode(){
    	int nMode = getCollisionDetectionModeT();
    	gviCollisionDetectionMode r = gviCollisionDetectionMode.gviCollisionDisable;
		for(gviCollisionDetectionMode e : gviCollisionDetectionMode.values()){
			if(e.getValue() == nMode){
				r = e;
				break;
			}
		} 
		return r;
    }
    
    public native void setCollisionDetectionMode(gviCollisionDetectionMode newVal);    
    
    public native void flyToObject(int oid, gviActionCode code);
  
	private native boolean screenToWorldT(int x, int y, Double ix, Double iy, Double iz, Integer iObject, Integer iFid);
	private native boolean worldToScreenT(double worldX, double worldY, double worldZ, Double screenx, Double screeny, int mode, Boolean isInScreen);
	public IPickResult screenToWorld(float x, float y) {
		IPickResult r = new IPickResult();
		Double ix = 0.0, iy = 0.0, iz = 0.0;
		Integer iObject = 0, iFid = -1;
		boolean bRet =  screenToWorldT((int)x, (int)y, ix, iy, iz, iObject, iFid);
		r.intersectX = ix;
		r.intersectY = iy;
		r.intersectZ = iz;
		r.fid = iFid;
		if(bRet) {			
			r.pickObject = RenderControl.get().objectManager.getObjectById(iObject);
		}

		/* 测试代码
		Double sx = 0.0, sy = 0.0;
		Boolean inScreen = false;
		boolean rrr = worldToScreenT(ix, iy, iz, sx, sy, 1, inScreen);
		*/
		
		return r;
	}

	public native float getNearClipPlane();
	public native void setNearClipPlane(float newVal);
	
	public native float getFarClipPlane();
	public native void setFarClipPlane(float newVal);

	public native boolean getAutoClipPlane();
	public native void setAutoClipPlane(boolean newVal);
	
	public native void stop();
	
	private native void enterOrthoModeT(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax, double heading, double tilt, double roll);
	
	public void enterOrthoMode(IEnvelope env, IEulerAngle angle) {
		this.enterOrthoModeT(env.getXMin(), env.getXMax(), env.getYMin(), env.getYMax(), env.getZMin(), env.getZMax(), 
				angle.getHeading(), angle.getTilt(), angle.getRoll());
	}
	
	public native void isOrthoMode();
	public native void leaveOrthoMode();
}
















