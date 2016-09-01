package com.gvitech.android;

public class ICameraTour extends IRObject{

	public ICameraTour(int oid) {
		super(oid);
	}
	
	private native boolean fromXmlT(int oid, String xmlStr);
	private native void playT(int oid);
	private native void pauseT(int oid);
	private native void stopT(int oid);
	private native double getTotalTimeT(int oid);
	private native int getWaypointsNumberT(int oid);
	private native double getTimeT(int oid);
	private native void setTimeT(int oid, double newVal);
	private native int getIndexT(int oid);
	private native void setIndexT(int oid, int newVal);
	private native boolean getAutoRepeatT(int oid);
	private native void setAutoRepeatT(int oid, boolean newVal);
	
		 
	public boolean fromXml( String xmlStr){
		return fromXmlT(this.objectId, xmlStr);
	}
	public void play(){
		playT(this.objectId);
	}
	public void pause(){
		pauseT(this.objectId);
	}
	public void stop(){
		stopT(this.objectId);
	}
	public double getTotalTime(){
		return getTotalTimeT(this.objectId);
	}
	public int getWaypointsNumber(){
		return getWaypointsNumberT(this.objectId);
	}
	public double getTime(){
		return getTimeT(this.objectId);
	}
	public void setTime( double newVal){
		setTimeT(this.objectId, newVal);
	}
	public int getIndex(){
		return getIndexT(this.objectId);
	}
	public void setIndex( int newVal){
		setIndexT(this.objectId, newVal);
	}
	public boolean getAutoRepeat(){
		return getAutoRepeatT(this.objectId);
	}
	public void setAutoRepeat( boolean newVal){
		setAutoRepeatT(this.objectId, newVal);
	}
	
}
