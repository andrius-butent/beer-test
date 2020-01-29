package com.satalia.beer.model;

import javax.persistence.*;
import java.math.BigDecimal;

/*
	This class will contain geo coordinates (latitude, longitude)
*/
@Entity
@Table(name="Geo_Codes")
public class Coordinates {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name="Brewery_Id")
	private Long breweryId;

	@Column(name="Latitude")
	private Double latitude;

	@Column(name="Longitude")
	private Double longitude;

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
}