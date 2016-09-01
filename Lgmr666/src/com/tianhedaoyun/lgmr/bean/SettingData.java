package com.tianhedaoyun.lgmr.bean;

import java.io.Serializable;

public class SettingData implements Serializable {

	private int temperature;
	private int pressure;
	private String instrument;
	private String unit;
	private String unitDecimal;
	private boolean playSounds;
	private boolean changeTextColor;

	public SettingData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SettingData(int temperature, int pressure, String instrument, String unit, String unitDecimal,
			boolean playSounds, boolean changeTextColor) {
		super();
		this.temperature = temperature;
		this.pressure = pressure;
		this.instrument = instrument;
		this.unit = unit;
		this.unitDecimal = unitDecimal;
		this.playSounds = playSounds;
		this.changeTextColor = changeTextColor;
	}

	public SettingData(int temperature, int pressure, boolean changeTextColor, boolean playSounds) {
		super();
		this.temperature = temperature;
		this.pressure = pressure;
		this.playSounds = playSounds;
		this.changeTextColor = changeTextColor;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitDecimal() {
		return unitDecimal;
	}

	public void setUnitDecimal(String unitDecimal) {
		this.unitDecimal = unitDecimal;
	}

	public boolean isPlaySounds() {
		return playSounds;
	}

	public void setPlaySounds(boolean playSounds) {
		this.playSounds = playSounds;
	}

	public boolean isChangeTextColor() {
		return changeTextColor;
	}

	public void setChangeTextColor(boolean changeTextColor) {
		this.changeTextColor = changeTextColor;
	}

}
