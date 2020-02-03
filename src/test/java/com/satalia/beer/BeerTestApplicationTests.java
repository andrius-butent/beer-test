package com.satalia.beer;

import com.satalia.beer.model.BreweryCodes;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerTestApplicationTests {

	private static final double LATITUDE = 51.742503;
	private static final double LONGITUDE = 19.432956;

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
}
