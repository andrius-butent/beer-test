package com.satalia.beer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Breweries")
public class Brewery {
	@Id
	private Long id;

	@Column(name="Name")
	private Long breweryName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBreweryName() {
		return breweryName;
	}

	public void setBreweryName(Long breweryName) {
		this.breweryName = breweryName;
	}
}
