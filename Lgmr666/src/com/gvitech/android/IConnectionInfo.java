package com.gvitech.android;

import com.gvitech.android.EnumValue.gviConnType;

public class IConnectionInfo {
	protected long _piObject = 0;
	
	private native int createT();
	public static IConnectionInfo create(){
		IConnectionInfo ci = new IConnectionInfo();
		ci._piObject = ci.createT();
		return ci;
	}	
	
	private native int getTypeT(long connInfo);
	public gviConnType getType() {
		int n = this.getTypeT(this._piObject);
		gviConnType r = gviConnType.gviConnectionUnknown;
		for(gviConnType e : gviConnType.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}
	
	private native void setTypeT(long connInfo, gviConnType newVal);
	public void setType(gviConnType newVal) {
		this.setTypeT(this._piObject, newVal);
	}
	
	private native void freePtrObject(long connInfo);
	public void free() {
		this.freePtrObject(this._piObject);
	}
	
	private native String getDataBaseNameT(long connInfo);
	public String getDataBaseName()
	{
		return this.getDataBaseNameT(this._piObject);
	}
	
	private native void setDataBaseNameT(long connInfo, String newVal);
	public void setDataBaseName(String newVal)
	{
		this.setDataBaseNameT(this._piObject, newVal);
	}
	
	private native void setServerT(long connInfo, String newVal);
	public void setServer(String newVal)
	{
		this.setServerT(this._piObject, newVal);
	}
}
