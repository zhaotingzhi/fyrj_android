package com.tianhedaoyun.lgmr.bean;

public class ShiShiPoint {

	private double x;

	private double y;

	private double z;

	private static ShiShiPoint _instance;

	public static ShiShiPoint getInstance() {
		if (ShiShiPoint._instance == null) {
			ShiShiPoint._instance = new ShiShiPoint();
		}
		return ShiShiPoint._instance;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
