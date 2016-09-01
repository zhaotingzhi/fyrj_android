package com.gvitech.android;

import com.gvitech.android.EnumValue.*;


public abstract class IRObject {
	
	protected int objectId;
	private native int getTypeT(int oid);
	private native String getGuidT(int oid);
	private native String getClientDataT(int oid);
	private native void setClientDataT(int oid, String newVal);
	private native int getAttributeMaskT(int oid);
	private native int setAttributeMaskT(int oid, gviAttributeMask newVal);
	private native String getNameT(int oid);
	private native void setNameT(int oid, String newVal);

		
	
	public IRObject(int id){
		objectId = id;
	}
	
	public int objectId(){
		return objectId;
	}
			
	
	public gviObjectType getType(){
		int nType = getTypeT(this.objectId);
		gviObjectType r = gviObjectType.gviObjectNone;
		for(gviObjectType e : gviObjectType.values()){
			if(e.getValue() == nType){
				r = e;
				break;
			}
		}
		return r;
	}
	
			
	public String getGuid(){
		return getGuidT(this.objectId);
	}
	
	public String getClientData()	{
		return getClientDataT(this.objectId);
	}
	
	public void setClientData(String newVal)	{
		setClientDataT(this.objectId, newVal);
	}
	
	public gviAttributeMask getAttributeMask()	{	
		int n = getAttributeMaskT(this.objectId);
		gviAttributeMask r = gviAttributeMask.gviAttributeHighlight;
		for(gviAttributeMask e : gviAttributeMask.values()){
			if(e.getValue() == n){
				r = e;
				break;
			}
		}
		return r;
	}
	
	public void setAttributeMask(gviAttributeMask newVal)	{
		setAttributeMaskT(this.objectId, newVal);
	}
	
	public String getName()	{
		return getNameT(this.objectId);
	}
	
	public void setName(String newVal)	{
		setNameT(this.objectId, newVal);
	}
}





