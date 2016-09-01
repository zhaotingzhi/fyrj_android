package com.gvitech.android;

import com.gvitech.android.EnumValue.gviDataSetType;

public class IFeatureDataset {
	protected long _piObject = 0;

	private native String[] getNamesByTypeT(long dataset, Integer iTableCounts, gviDataSetType typeVal);
	public String[] getNamesByType(Integer iTableCounts, gviDataSetType typeVal) {
		return this.getNamesByTypeT(this._piObject, iTableCounts, typeVal);
	}
	
	private native int openFeatureClassT(long dataset, String newVal);
	public IFeatureClass openFeatureClass(String newVal){
		IFeatureClass fc = new IFeatureClass();
		fc._piObject = this.openFeatureClassT(this._piObject, newVal);
		return fc;
	}
	public long get_piObject(){
		return _piObject;
	}
	
}
