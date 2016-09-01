package com.gvitech.android;

public class IImagePointSymbol extends IPointSymbol{
	private native long createT();
	public static IImagePointSymbol create(){
		IImagePointSymbol cs = new IImagePointSymbol();
		cs._piObject = cs.createT();
		return cs;
	}
	
	private native void setImageNameT(long geometrySymbol, String imageName);
	public void setImageName(String imageName){
		this.setImageNameT(this._piObject, imageName);
	}
}
