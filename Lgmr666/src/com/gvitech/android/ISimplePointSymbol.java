package com.gvitech.android;

public class ISimplePointSymbol extends IPointSymbol{
	private native long createT();
	public static ISimplePointSymbol create(){
		ISimplePointSymbol cs = new ISimplePointSymbol();
		cs._piObject = cs.createT();
		return cs;
	}
	
	private native void setFillColorT(long geometrySymbol, int color);
	public void setFillColor(int color){
		this.setFillColorT(this._piObject, color);
	}
	
	private native void setOutlineColorT(long geometrySymbol, int color);
	public void setOutlineColor(int color){
		this.setOutlineColorT(this._piObject, color);
	}
}
