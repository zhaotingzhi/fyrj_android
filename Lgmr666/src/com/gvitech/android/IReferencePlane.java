package com.gvitech.android;

public class IReferencePlane extends IRObject{

	public IReferencePlane(int oid) {
		super(oid);
	}
	
	public native double getPlaneHeight();
	public native void setPlaneHeight(double newVal);
}
