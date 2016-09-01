package com.gvitech.android;

public class ICacheManager {

	public native boolean getFileCacheEnabled();
	
	public native void setFileCacheEnabled(boolean newVal);
	
	public native String getFileCachePath();
	
	public native void setFileCachePath(String newVal);
	
	public native String getTileCacheFileName(String newVal);
}
