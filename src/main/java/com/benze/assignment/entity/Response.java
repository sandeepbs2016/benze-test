package com.benze.assignment.entity;

import java.util.ArrayList;
import java.util.List;

public class Response {

	private String transactionId;
	private String vin;
	private String source;
	private String destination;
	
	private int distance;
	private int currentChargeLevel;
	
	private boolean isChargingRequired;
	
	private List<String> chargingStations = new ArrayList<>();
	
	private List<Errors> errors = new ArrayList<>();

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getCurrentChargeLevel() {
		return currentChargeLevel;
	}

	public void setCurrentChargeLevel(int currentChargeLevel) {
		this.currentChargeLevel = currentChargeLevel;
	}

	public boolean isChargingRequired() {
		return isChargingRequired;
	}

	public void setChargingRequired(boolean isChargingRequired) {
		this.isChargingRequired = isChargingRequired;
	}

	/**
	 * @return the chargingStations
	 */
	public List<String> getChargingStations() {
		return chargingStations;
	}

	/**
	 * @param chargingStations the chargingStations to set
	 */
	public void setChargingStations(List<String> chargingStations) {
		this.chargingStations = chargingStations;
	}

	/**
	 * @return the errors
	 */
	public List<Errors> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<Errors> errors) {
		this.errors = errors;
	}

	
}
