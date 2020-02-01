package com.satalia.beer.repository;

import com.satalia.beer.model.BreweryCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreweyCodesRepository extends JpaRepository<BreweryCodes, Long> {
}
