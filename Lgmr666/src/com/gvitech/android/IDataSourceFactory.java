package com.gvitech.android;

public class IDataSourceFactory {
	
	private native boolean hasDataSourceT(long connInfo);
	public boolean hasDataSource(IConnectionInfo ci) {
		return this.hasDataSourceT(ci._piObject);
	}
	
	private native String[] getDataBaseNamesT(long connInfo, boolean bOnlyFdb, Integer iDbCounts);
	public String[] getDataBaseNames(IConnectionInfo ci, boolean bOnlyFdb, Integer iDbCounts) {
		return this.getDataBaseNamesT(ci._piObject, bOnlyFdb, iDbCounts);
	}
	
	private native int openDataSourceT(long connInfo);
	public IDataSource openDataSource(IConnectionInfo ci){
		IDataSource ds = new IDataSource();
		ds._piObject = this.openDataSourceT(ci._piObject);
		return ds;
	}
}
