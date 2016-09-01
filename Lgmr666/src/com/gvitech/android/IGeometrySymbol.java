package com.gvitech.android;

import com.gvitech.android.EnumValue.gviGeometrySymbolType;

public abstract class IGeometrySymbol {
	protected long _piObject = 0;
	
	private native int getSymbolTypeT(long geometrySymbol);
	public gviGeometrySymbolType getSymbolType() {
		int n = this.getSymbolTypeT(this._piObject);
		gviGeometrySymbolType r = gviGeometrySymbolType.gviGeoSymbolPoint;
		for(gviGeometrySymbolType e : gviGeometrySymbolType.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}	
	
}