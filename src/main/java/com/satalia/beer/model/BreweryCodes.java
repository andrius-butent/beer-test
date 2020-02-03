package com.satalia.beer.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
	This class will contain geo coordinates (latitude, longitude)
	and other data for travelling to the next brewery
*/
@Entity
@Table(name="Geo_Codes")
public class BreweryCodes {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="Brewery_Id")
	private Long breweryId;

	@Column(name="Latitude")
	private Double latitude;

	@Column(name="Longitude")
	private Double longitude;

	@Transient
	private List<Long> parentIds = new ArrayList<>();

	@Transient
	private Double totalDistance;

	@Transient
	private List<BreweryCodes> nextPossibleBreweries = new ArrayList<>();

	@Transient
	private List<Beers> beers = new ArrayList<>();

	public BreweryCodes() {
		this(0L, 0d, 0d);
	}

	public BreweryCodes(Long breweryId, Double latitude, Double longitude) {
		this.breweryId = breweryId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.totalDistance = 0d;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBreweryId() {
		return breweryId;
	}

	public void setBreweryId(Long breweryId) {
		this.breweryId = breweryId;
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

	public List<Long> getParentIds() {
		return parentIds;
	}

	public void setParentIds(List<Long> parentIds) {
		this.parentIds = parentIds;
	}

	public Double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public List<BreweryCodes> getNextPossibleBreweries() {
		return nextPossibleBreweries;
	}

	public void setNextPossibleBreweries(List<BreweryCodes> nextPossibleBreweries) {
		this.nextPossibleBreweries = nextPossibleBreweries;
	}

	public List<Beers> getBeers() {
		return beers;
	}

	public void setBeers(List<Beers> beers) {
		this.beers = beers;
	}

	public void addBeer(Beers beer) {
		this.beers.add(beer);
	}

	public int getBeerCount() {
		return this.beers.size();
	}
}