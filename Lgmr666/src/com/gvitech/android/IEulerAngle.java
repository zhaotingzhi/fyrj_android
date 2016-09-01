package com.gvitech.android;

public class IEulerAngle {
	
	private double heading = 0.0;
	private double tilt = 0.0;
	private double roll = 0.0;
	
	public double getHeading() {
		return heading;
	}
	public void setHeading(double heading) {
		this.heading = heading;
	}
	public double getTilt() {
		return tilt;
	}
	public void setTilt(double tilt) {
		this.tilt = tilt;
	}
	public double getRoll() {
		return roll;
	}
	public void setRoll(double roll) {
		this.roll = roll;
	}
	
	public IEulerAngle(){
		
	}

	public IEulerAngle(double heading, double tilt, double roll) {
		this.set(heading, tilt, roll);
	}
	
	public void set(double heading, double tilt, double roll) {
		this.heading = heading;
		this.tilt = tilt;
		this.roll = roll;
	}

}
