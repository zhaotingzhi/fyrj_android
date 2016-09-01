package com.gvitech.android;

public abstract class IRenderGeometry extends IRenderable {

	public IRenderGeometry(int oid) {
		super(oid);
	}
	
	private native String getFdeGeometryT(int oid);
	public String getFdeGeometry()	{
		return getFdeGeometryT(this.objectId);
	}
	
	private native void setFdeGeometryT(int oid, String wkt);
	public void setFdeGeometry(String wkt)	{
		setFdeGeometryT(this.objectId, wkt);
	}
	
	private native void setXT(int oid, double x);
	public void setX(double x)	{
		setXT(this.objectId, x);
	}
	
	private native void setYT(int oid, double y);
	public void setY(double y)	{
		setYT(this.objectId, y);
	}
	
	private native void setZT(int oid, double z);
	public void setZ(double z)	{
		setZT(this.objectId, z);
	}
}
