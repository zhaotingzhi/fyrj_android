package com.tianhedaoyun.lgmr.bean;

import java.io.Serializable;

public class Point implements Serializable {

	private int oid;
	private String PointRole;
	private String type;
	private String PointNumber;
	private String state;
	private String comment;

	private double x;
	private double y;
	private double z;
	private static Point _instance;


	public Point(int oid, String pointRole, String type, String pointNumber, String state, String comment, double x,
			double y, double z) {
		super();
		this.oid = oid;
		PointRole = pointRole;
		this.type = type;
		PointNumber = pointNumber;
		this.state = state;
		this.comment = comment;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Point getInstance() {
		if (Point._instance == null) {
			Point._instance = new Point();
		}
		return Point._instance;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getPointRole() {
		return PointRole;
	}

	public void setPointRole(String pointRole) {
		PointRole = pointRole;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPointNumber() {
		return PointNumber;
	}

	public void setPointNumber(String pointNumber) {
		PointNumber = pointNumber;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
