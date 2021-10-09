package com.benze.assignment.entity;

import java.util.ArrayList;
import java.util.List;

public class ChargingStation {

	private String source;
	private String distanceFromSource;

	private List<Station> chargingStations = new ArrayList<>();

	private String error;

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the distanceFromSource
	 */
	public String getDistanceFromSource() {
		return distanceFromSource;
	}

	/**
	 * @param distanceFromSource the distanceFromSource to set
	 */
	public void setDistanceFromSource(String distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
	}

	/**
	 * @return the chargingStations
	 */
	public List<Station> getChargingStations() {
		return chargingStations;
	}

	/**
	 * @param chargingStations the chargingStations to set
	 */
	public void setChargingStations(List<Station> chargingStations) {
		this.chargingStations = chargingStations;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	
	

}
