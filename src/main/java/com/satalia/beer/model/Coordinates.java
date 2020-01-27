package com.satalia.beer.model;

import java.math.BigDecimal;

/*
	This class will contain geo coordinates (latitude, longitude)
*/

public class Coordinates {

	BigDecimal latitude;
	BigDecimal longitude;

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