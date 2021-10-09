package com.benze.assignment.entity;

public class Car {

	private String vin;
	private String error;
	private int currentChargeLevel;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getCurrentChargeLevel() {
		return currentChargeLevel;
	}

	public void setCurrentChargeLevel(int currentChargeLevel) {
		this.currentChargeLevel = currentChargeLevel;
	}

	@Override
	public String toString() {
		return "CarEntity [vin=" + vin + ", error=" + error + ", currentChargeLevel=" + currentChargeLevel + "]";
	}

}
