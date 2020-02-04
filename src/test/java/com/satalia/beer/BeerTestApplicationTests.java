package com.satalia.beer;

import com.satalia.beer.model.Beers;
import com.satalia.beer.model.Brewery;
import com.satalia.beer.model.BreweryCodes;
import com.satalia.beer.service.BeersService;
import com.satalia.beer.service.BreweryCodesService;
import com.satalia.beer.service.BreweryService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BeerTestApplicationTests {

	private static final double LATITUDE = 51.742503;
	private static final double LONGITUDE = 19.432956;

	@Autowired
	private BreweryCodesService breweryCodesService;

	@Autowired
	private BeersService beersService;

	@Autowired
	private BreweryService breweryService;

	@Test
	public void when_BothBreweryIsNullForCalculatingDistance_Expect_ZeroDistance() {
		// given
		BreweryCodes breweryA = null;
		BreweryCodes breweryB = null;
		Double expectedDistance = (double) 0;

		// when
		Double distance = BeerUtils.haversine(breweryA, breweryB);

		//then
		Assert.assertEquals(expectedDistance, distance);
	}

	@Test
	public void when_OneBreweryIsNullForCalculatingDistance_Expect_ZeroDistance() {
		// given
		BreweryCodes breweryA = new BreweryCodes();
		breweryA.setLatitude(LATITUDE);
		breweryA.setLongitude(LONGITUDE);
		BreweryCodes breweryB = null;
		Double expectedDistance = (double) 0;

		// when
		Double distance = BeerUtils.haversine(breweryA, breweryB);

		//then
		Assert.assertEquals(expectedDistance, distance);
	}

	@Test
	public void when_BreweriesIsNotNullForCalculatingDistance_Expect_PositiveDistance() {
		// given
		BreweryCodes breweryA = new BreweryCodes();
		breweryA.setLatitude(LATITUDE);
		breweryA.setLongitude(LONGITUDE);

		BreweryCodes breweryB = new BreweryCodes();
		breweryB.setLatitude(0d);
		breweryB.setLongitude(0d);

		// when
		double distance = BeerUtils.haversine(breweryA, breweryB);

		//then
		Assert.assertTrue(distance > 0);
	}

	@Test
	public void when_BeersAreSelectingFromDB_Expect_NotEmptyList() {
		// when
		List<Beers> beerList = beersService.getAllBeers();

		//then
		Assert.assertTrue(beerList.size() > 0);
	}

	@Test
	public void when_BreweriesAreSelectedFromDB_Expect_NotEmptyList() {
		// when
		List<Brewery> breweryList = breweryService.getAllBreweries();

		//then
		Assert.assertTrue(breweryList.size() > 0);
	}

	@Test
	public void when_BreweryCodesAreSelectedFromDB_Expect_NotEmptyList() {
		// when
		List<BreweryCodes> codes = breweryCodesService.getAllBreweryCodes();

		//then
		Assert.assertTrue(codes.size() > 0);
	}
}
