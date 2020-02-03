package com.satalia.beer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * All data from Beers table
 */

@Entity
@Table(name="Beers")
public class Beers {

	@Id
	private Long id;

	@Column(name="Brewery_Id")
	private Long breweryId;

	@Column(name="Beer_Name")
	private String beerName;

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

	public String getBeerName() {
		return beerName;
	}

	public void setBeerName(String beerName) {
		this.beerName = beerName;
	}
}
