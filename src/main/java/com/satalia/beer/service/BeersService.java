package com.satalia.beer.service;

import com.satalia.beer.model.Beers;
import com.satalia.beer.repository.BeersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeersService {
	@Autowired
	private BeersRepository repository;

	public List<Beers> getAllBeers() {
		return repository.findAll();
	}
}
