package com.gvitech.android;


public class ICurveSymbol extends IGeometrySymbol{

	private native long createT();
	public static ICurveSymbol create(){
		ICurveSymbol cs = new ICurveSymbol();
		cs._piObject = cs.createT();
		return cs;
	}
	
	private native void setColorT(long geometrySymbol, int color);
	public void setColor(int color){
		this.setColorT(this._piObject, color);
	}
	
	private native void setWidthT(long geometrySymbol, float width);
	public void setWidth(float width){
		this.setWidthT(this._piObject, width);
	}
	
	private native void setRepeatLengthT(long geometrySymbol, float repeatLength);
	public void setRepeatLength(float repeatLength){
		this.setRepeatLengthT(this._piObject, repeatLength);
	}
	
	private native void setImageNameT(long geometrySymbol, String imageName);
	public void setImageName(String imageName){
		this.setImageNameT(this._piObject, imageName);
	}
}