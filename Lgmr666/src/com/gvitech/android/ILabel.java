package com.gvitech.android;

public class ILabel extends IRenderable {

	public ILabel(int oid) {
		super(oid);
	}
	
	protected void finalize(){
		
	}
	
	public native String getTextT(int oid);
	public native void setTextT(int oid, String newVal);
	public native double getXT(int oid);
	public native double getYT(int oid);
	public native double getZT(int oid);
	
	public String getText()	{
		return getTextT(this.objectId);
	}
	
	public void setText(String newVal){
		setTextT(this.objectId, newVal);
	}
	
	public double getX(){
		return getXT(this.objectId);
	}
	
	public double getY(){
		return getYT(this.objectId);
	}
	
	public double getZ(){
		return getZT(this.objectId);
	}
}
