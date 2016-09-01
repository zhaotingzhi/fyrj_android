package com.gvitech.android;

import com.gvitech.android.EnumValue.*;


public class ISkyBox extends IRObject {
	
	public ISkyBox(int oid) {
		super(oid);
	}
	
	public native void setImagePath(gviSkyboxImageIndex index, String imagePath);
	
	private native int getWeatherT();
	public gviWeatherType getWeather(){
		int nRet = getWeatherT();
		return gviWeatherType.values()[nRet];
	}
	
	public native void setWeather(gviWeatherType weatherType);
	
	
	private native int getFogModeT();
	public gviFogMode getFogMode(){
		int nRet = getFogModeT();
		return gviFogMode.values()[nRet]; 
	}
	
	public native void setFogMode(gviFogMode fogMode);
	
	public native float getFogStartDistance();
	public native void setFogStartDistance(float newVal);
		
	public native float getFogEndDistance();
	public native void setFogEndDistance(float newVal);
	
	public native int getFogColor();
	public native void setFogColor(int newVal);
	
	
	public void setDefaultSkybox(){
		setImagePath(gviSkyboxImageIndex.gviSkyboxImageFront, "skybox/101_Front.png");
		setImagePath(gviSkyboxImageIndex.gviSkyboxImageBack, "skybox/101_Back.png");
		setImagePath(gviSkyboxImageIndex.gviSkyboxImageLeft,  "skybox/101_Left.png");
		setImagePath(gviSkyboxImageIndex.gviSkyboxImageRight, "skybox/101_Right.png");
		setImagePath(gviSkyboxImageIndex.gviSkyboxImageTop, "skybox/101_Top.png");
		setImagePath(gviSkyboxImageIndex.gviSkyboxImageBottom, "skybox/101_Bottom.png");
	}
}








