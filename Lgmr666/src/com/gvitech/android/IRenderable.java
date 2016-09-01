package com.gvitech.android;

import com.gvitech.android.EnumValue.*;


public abstract class IRenderable extends IRObject {
	
	public IRenderable(int oid) {
		super(oid);
	}
	
	private native int getDepthTestModeT(int oid);
	private native void setDepthTestModeT(int oid, gviDepthTestMode newVal);
	private native int getVisibleMaskT(int oid);
	private native void setVisibleMaskT(int oid, gviViewportMask newVal);
	
	private native double getMaxVisibleDistanceT(int oid);
	private native void setMaxVisibleDistanceT(int oid, double newVal);

	private native double getMaxViewingDistanceT(int oid);
	private native void setViewingDistanceT(int oid, double newVal);

	private native void highlightT(int oid, int color);
	private native void unhighlightT(int oid);
	
	private native int getMouseSelectMaskT(int oid);
	private native void setMouseSelectMaskT(int oid, gviViewportMask newVal);
	
	private native float getMinVisiblePixelsT(int oid);
	private native void setMinVisiblePixelsT(int oid, float newVal);
	
	public gviDepthTestMode getDepthTestMode(){		
		int nType = getDepthTestModeT(this.objectId);
		gviDepthTestMode r = gviDepthTestMode.gviDepthTestEnable;
		for(gviDepthTestMode e : gviDepthTestMode.values()){
			if(e.getValue() == nType){
				r = e;
				break;
			}
		}
		return r;
	}
	
	public void setDepthTestMode(gviDepthTestMode newVal){
		setDepthTestModeT(this.objectId, newVal);
	}
			
	public gviViewportMask getVisibleMask(){		
		int nType = getVisibleMaskT(this.objectId);
		gviViewportMask r = gviViewportMask.gviView0;
		for(gviViewportMask e : gviViewportMask.values()){
			if(e.getValue() == nType){
				r = e;
				break;
			}
		}
		return r;
	}
	
	public void setVisibleMask(gviViewportMask newVal){
		setVisibleMaskT(this.objectId, newVal);
	}
	
	public double getMaxVisibleDistance(){
		return getMaxVisibleDistanceT(this.objectId);
	}
	public void setMaxVisibleDistance(double newVal){
		setMaxVisibleDistanceT(this.objectId, newVal);
	}

	public double getMaxViewingDistance(){
		return getMaxViewingDistanceT(this.objectId);
	}
	public void setViewingDistance(double newVal){
		setViewingDistanceT(this.objectId, newVal);
	}

	public void highlight(int color){
		highlightT(this.objectId, color);
	}
	public void unhighlight(){
		unhighlightT(this.objectId);
	}

	public gviViewportMask getMouseSelectMask(){		
		int nType = getMouseSelectMaskT(this.objectId);
		gviViewportMask r = gviViewportMask.gviView0;
		for(gviViewportMask e : gviViewportMask.values()){
			if(e.getValue() == nType){
				r = e;
				break;
			}
		}
		return r;
	}
	
	public void setMouseSelectMask(gviViewportMask newVal){
		setMouseSelectMaskT(this.objectId, newVal);
	}
	
	public float getMinVisiblePixels(){
		return getMinVisiblePixelsT(this.objectId);
	}
	public void setMinVisiblePixels(float newVal){
		setMinVisiblePixelsT(this.objectId, newVal);
	}
	
	/**
	 * @example:
	 * 返回的数组元素：xMin, xMax, yMin, yMax, zMin, zMax
	 */
    public native double[] getEnvelopeT(int oid);
	public IEnvelope getEnvelope(){
		double[] value = getEnvelopeT(this.objectId);
		IEnvelope env = new IEnvelope();
		env.set(value[0], value[2], value[4], value[1], value[3], value[5]);
		return env;
	}
}














