package com.gvitech.android;

public class IEnvelope {
	private double xMin = 0.0;
	private double yMin = 0.0;
	private double zMin = 0.0;
	private double xMax = 0.0;
	private double yMax = 0.0;
	private double zMax = 0.0;
	
	public double getXMin() {
		return xMin;
	}
	public void setXMin(double x) {
		this.xMin = x;
	}
	public double getYMin() {
		return yMin;
	}
	public void setYMin(double y) {
		this.yMin = y;
	}
	public double getZMin() {
		return zMin;
	}
	public void setZMin(double z) {
		this.zMin = z;
	}
	public double getXMax() {
		return xMax;
	}
	public void setXMax(double x) {
		this.xMax = x;
	}
	public double getYMax() {
		return yMax;
	}
	public void setYMax(double y) {
		this.yMax = y;
	}
	public double getZMax() {
		return zMax;
	}
	public void setZMax(double z) {
		this.zMax = z;
	}
	
	public IEnvelope(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax){
		this.set(xMin, yMin, zMin, xMax, yMax, zMax);
	}

	public IEnvelope(){
		
	}
	
	public void set(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		this.xMin = xMin;
		this.yMin = yMin;
		this.zMin = zMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.zMax = zMax;
	}
	
	public boolean isValid()
	{
		return this.xMax >= this.xMin && this.yMax >= this.yMin && this.zMax >= this.zMin;
	}
	
	public void expandByEnvelope(IEnvelope newVal)
	{
		if(!newVal.isValid()) return;
		
		if(newVal.getXMin() < this.xMin) this.xMin = newVal.getXMin();
		if(newVal.getXMax() > this.xMax) this.xMax = newVal.getXMax();
		
		if(newVal.getYMin() < this.yMin) this.yMin = newVal.getYMin();
		if(newVal.getYMax() > this.yMax) this.yMax = newVal.getYMax();
		
		if(newVal.getZMin() < this.zMin) this.zMin = newVal.getZMin();
		if(newVal.getZMax() > this.zMax) this.zMax = newVal.getZMax();
	}
	
	public void SetByEnvelope(IEnvelope newVal)
	{
		this.xMin = newVal.getXMin();
		this.xMax = newVal.getXMax();
		
		this.yMin = newVal.getYMin();
		this.yMax = newVal.getYMax();
		
		this.zMin = newVal.getZMin();
		this.zMax = newVal.getZMax();
	}
}
