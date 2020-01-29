package com.satalia.beer.service;

import com.satalia.beer.model.Coordinates;
import com.satalia.beer.repository.CoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinatesService {

	@Autowired
	private CoordinatesRepository repository;

	public List<Coordinates> getAllBreweriesCoordinates() {
		return repository.findAll();
	}

}