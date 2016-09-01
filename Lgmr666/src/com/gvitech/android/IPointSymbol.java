package com.gvitech.android;

public abstract class IPointSymbol extends IGeometrySymbol{
	private native void setColorT(long geometrySymbol, int color);
	public void setColor(int color){
		this.setColorT(this._piObject, color);
	}
	
	private native void setSizeT(long geometrySymbol, int size);
	public void setSize(int size){
		this.setSizeT(this._piObject, size);
	}
}
