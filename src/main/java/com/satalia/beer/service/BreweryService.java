package com.satalia.beer.service;

import com.satalia.beer.model.Brewery;
import com.satalia.beer.repository.BreweryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreweryService {

	@Autowired
	private BreweryRepository repository;

	public List<Brewery> getAllBreweries() {
		return repository.findAll();
	}

	public List<Brewery> getAllBreweriesById(List<Long> ids) {
		return repository.findAllById(ids);
	}
}
