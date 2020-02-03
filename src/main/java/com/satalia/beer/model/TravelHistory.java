package com.satalia.beer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will contain all information about traveled distance between previous and current breweries
 *
 */

public class TravelHistory {

	private int collectedBeers;
	private Double latitude;
	private Double longitude;
	private double traveledDistance;
	private List<Long> parents = new ArrayList<>();

	public TravelHistory(Double latitude, Double longitude, double traveledDistance, int collectedBeers) {
		this.collectedBeers = collectedBeers;
		this.latitude = latitude;
		this.longitude = longitude;
		this.traveledDistance = traveledDistance;
	}

	public int getCollectedBeers() {
		return collectedBeers;
	}

	public void setCollectedBeers(int collectedBeers) {
		this.collectedBeers = collectedBeers;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public double getTraveledDistance() {
		return traveledDistance;
	}

	public void setTraveledDistance(double traveledDistance) {
		this.traveledDistance = traveledDistance;
	}

	public List<Long> getParents() {
		return parents;
	}

	public void setParents(List<Long> parents) {
		this.parents = parents;
	}
}
