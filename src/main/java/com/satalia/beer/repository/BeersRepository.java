package com.satalia.beer.repository;

import com.satalia.beer.model.Beers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeersRepository extends JpaRepository<Beers, Long> {
}
