package com.gvitech.android;

public class IDataSource {
	protected long _piObject = 0;
	
	private native String[] getFeatureDatasetNamesT(long dataSource, Integer iDbCounts);
	public String[] getFeatureDatasetNames(Integer iDbCounts) {
		return this.getFeatureDatasetNamesT(this._piObject, iDbCounts);
	}
	
	private native int openFeatureDatasetT(long dataSource, String newVal);
	public IFeatureDataset openFeatureDataset(String newVal){
		IFeatureDataset dset = new IFeatureDataset();
		dset._piObject = this.openFeatureDatasetT(this._piObject, newVal);
		return dset;
	}
	public long get_piObject(){
		return _piObject;
	}
}
