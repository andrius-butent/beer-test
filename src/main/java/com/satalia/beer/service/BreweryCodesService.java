package com.satalia.beer.service;

import com.satalia.beer.model.BreweryCodes;
import com.satalia.beer.repository.BreweyCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreweryCodesService {

	@Autowired
	private BreweyCodesRepository repository;

	public List<BreweryCodes> getAllBreweryCodes() {
		return repository.findAll();
	}

}