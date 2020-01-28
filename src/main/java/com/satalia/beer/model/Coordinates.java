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
	private BigDecimal latitude;

	@Column(name="Longitude")
	private BigDecimal longitude;

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

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
}