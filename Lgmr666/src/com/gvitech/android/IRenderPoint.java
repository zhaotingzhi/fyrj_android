package com.gvitech.android;

public class IRenderPoint extends IRenderGeometry {

	public IRenderPoint(int oid) {
		super(oid);
	}
	
	public native String getImageNameT(int oid);
	
	public String getImageName(){
		return this.getImageNameT(this.objectId);
	}
}
