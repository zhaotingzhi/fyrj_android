package com.tianhedaoyun.lgmr.bean;

import java.io.Serializable;

public class StationData implements Serializable {

	private double robot_height;
	private double prism_height;

	private Point control_point;
	private Point backsight_point;

	private double houshi_ha;
	private double houshi_va;

	private static StationData _instance;

	public static StationData getInstance() {
		if (StationData._instance == null) {
			StationData._instance = new StationData();
		}
		return StationData._instance;
	}

	public double getHoushi_ha() {
		return houshi_ha;
	}

	public void setHoushi_ha(double houshi_ha) {
		this.houshi_ha = houshi_ha;
	}

	public double getHoushi_va() {
		return houshi_va;
	}

	public void setHoushi_va(double houshi_va) {
		this.houshi_va = houshi_va;
	}

	public double getRobot_height() {
		return robot_height;
	}

	public void setRobot_height(double robot_height) {
		this.robot_height = robot_height;
	}

	public double getPrism_height() {
		return prism_height;
	}

	public void setPrism_height(double prism_height) {
		this.prism_height = prism_height;
	}

	public Point getControl_point() {
		return control_point;
	}

	public void setControl_point(Point control_point) {
		this.control_point = control_point;
	}

	public Point getBacksight_point() {
		return backsight_point;
	}

	public void setBacksight_point(Point backsight_point) {
		this.backsight_point = backsight_point;
	}

}
