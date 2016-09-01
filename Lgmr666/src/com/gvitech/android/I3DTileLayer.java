package com.gvitech.android;

public class I3DTileLayer extends IRenderable {

	public I3DTileLayer(int oid) {
		super(oid);
	}
	
	private native boolean getGrayShowT(int oid);
	private native void setGrayShowT(int oid, boolean newVal);
	private native float getGrayScalarT(int oid);
	private native void setGrayScalarT(int oid, float newVal);
	
	
	public boolean getGrayShow(){
		return getGrayShowT(this.objectId);
	}
	public void setGrayShow(boolean newVal){
		setGrayShowT(this.objectId, newVal);
	}
	
	public float getGrayScalar(){
		return getGrayScalarT(this.objectId);
	}
	public void setGrayScalar(float newVal){
		setGrayScalarT(this.objectId, newVal);
	}
	
}
