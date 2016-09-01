package com.gvitech.android;

public class ISurfaceSymbol extends IGeometrySymbol{

	private native long createT();
	public static ISurfaceSymbol create(){
		ISurfaceSymbol cs = new ISurfaceSymbol();
		cs._piObject = cs.createT();
		return cs;
	}
	
	private native void setColorT(long geometrySymbol, int color);
	public void setColor(int color){
		this.setColorT(this._piObject, color);
	}
	
	private native void setBoundarySymbolT(long geometrySymbol, long symbol);
	public void setBoundarySymbol(ICurveSymbol symbol){
		this.setBoundarySymbolT(this._piObject, symbol._piObject);
	}
	
	private native void setRepeatLengthUT(long geometrySymbol, float repeatLength);
	public void setRepeatLengthU(float repeatLength){
		this.setRepeatLengthUT(this._piObject, repeatLength);
	}
	
	private native void setRepeatLengthVT(long geometrySymbol, float repeatLength);
	public void setRepeatLengthV(float repeatLength){
		this.setRepeatLengthVT(this._piObject, repeatLength);
	}
	
	private native void setImageNameT(long geometrySymbol, String imageName);
	public void setImageName(String imageName){
		this.setImageNameT(this._piObject, imageName);
	}
}