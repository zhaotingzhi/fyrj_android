package com.gvitech.android;

public class IFeatureClass {
	protected long _piObject = 0;

	private native int searchT(long featureclass, long filter, boolean bReuseRow);
	public IFdeCursor search(IQueryFilter filter, boolean bReuseRow){
		IFdeCursor cursor = new IFdeCursor();
		cursor._piObject = this.searchT(this._piObject, filter._piObject, bReuseRow);
		return cursor;
	}
	public long get_piObject(){
		return _piObject;
	}
	
}
