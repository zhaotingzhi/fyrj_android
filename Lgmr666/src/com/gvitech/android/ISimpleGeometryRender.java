package com.gvitech.android;

public class ISimpleGeometryRender extends IGeometryRender{

	private native long createT();
	public static ISimpleGeometryRender create(){
		ISimpleGeometryRender gr = new ISimpleGeometryRender();
		gr._piObject = gr.createT();
		return gr;
	}
	
	private native void setSymbolT(long geometryRender, long symbol);
	public void setSymbol(IGeometrySymbol symbol){
		this.setSymbolT(this._piObject, symbol._piObject);
	}
}