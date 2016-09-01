package com.gvitech.android;

import com.gvitech.android.EnumValue.gviRenderType;

public abstract class IGeometryRender {
	protected long _piObject = 0;
	
	private native int getRenderTypeT(long geometryRender);
	public gviRenderType getRenderType() {
		int n = this.getRenderTypeT(this._piObject);
		gviRenderType r = gviRenderType.gviRenderSimple;
		for(gviRenderType e : gviRenderType.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}	
	
	private native String getRenderGroupFieldT(long oid);
	public String getRenderGroupField()	{
		return getRenderGroupFieldT(this._piObject);
	}
	
	private native void setRenderGroupFieldT(long oid, String fieldName);
	public void setRenderGroupField(String fieldName)	{
		setRenderGroupFieldT(this._piObject, fieldName);
	}
}


