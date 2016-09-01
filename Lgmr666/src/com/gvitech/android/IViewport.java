package com.gvitech.android;

import com.gvitech.android.EnumValue.gviViewportMode;

public class IViewport {

	public native boolean getLogoVisible();
	public native void setLogoVisible(boolean newVal);
	
	public native boolean getCompassVisible();
	public native void setCompassVisible(boolean newVal);
	
	public native void setCompassOffset(float ox, float oy);
	public native void setThumbtackOffset(float ox, float oy);	
	
	private native int getViewportModeT();
	
	public gviViewportMode getViewportMode(){
		int nMode = getViewportModeT();
		gviViewportMode r = gviViewportMode.gviViewportSinglePerspective;
		for(gviViewportMode e : gviViewportMode.values()){
			if(e.getValue() == nMode){
				r = e;
				break;
			}
		} 
		return r;
	}
	
	public native void setViewportMode(gviViewportMode newVal);
}
