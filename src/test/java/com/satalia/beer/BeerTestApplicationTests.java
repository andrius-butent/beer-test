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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BeerTestApplicationTests {

	private static final double LATITUDE = 51.742503;
	private static final double LONGITUDE = 19.432956;

	@Autowired
	private BreweryCodesService breweryCodesService;

	@Autowired
	private BeersService beersService;

	@Autowired
	private BreweryService breweryService;

	@Autowired
	private MockMvc mockMvc;

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

	@Test
	public void when_CalculatingDistanceWeightAndBreweriesListIsEmpty_Expect_NullValue() {
		// given
		List<BreweryCodes> breweryCodes = new ArrayList<>();
		BreweryCodes breweryCode = new BreweryCodes();
		breweryCode.setBreweryId(1L);
		breweryCode.setLongitude(LONGITUDE);
		breweryCode.setLatitude(LATITUDE);
		breweryCodes.add(breweryCode);

		//when
		BreweryCodes bc = BeerUtils.getBreweryByWeight(breweryCodes, new BreweryCodes(), Arrays.asList(0L, 1L));

		//then
		Assert.assertNull(bc);
	}

	@Test
	public void when_BreweriesListIsNotEmpty_Expect_WeightIsCalculated() {
		// given
		List<BreweryCodes> breweryCodes = new ArrayList<>();
		BreweryCodes breweryCode1 = new BreweryCodes();
		breweryCode1.setBreweryId(1L);
		breweryCode1.setLongitude(LONGITUDE);
		breweryCode1.setLatitude(LATITUDE);
		breweryCode1.setBeers(Arrays.asList(new Beers(), new Beers()));
		breweryCodes.add(breweryCode1);


		// we need brewery with coordinates to get distance
		BreweryCodes breweryCode2 = new BreweryCodes();
		breweryCode2.setBreweryId(3L);
		breweryCode2.setLongitude(0.0);
		breweryCode2.setLatitude(0.0);

		//when
		BreweryCodes bc = BeerUtils.getBreweryByWeight(breweryCodes, breweryCode2, Arrays.asList(0L, 4L));

		//then
		Assert.assertNotNull(bc);
	}

	@Test
	public void when_MainFormIsOpened_Expect_HttpStatus200() throws Exception {
		this.mockMvc.perform(get("/beer/form"))
			.andExpect(status().isOk())
			.andExpect(view().name("main-form"))
			.andExpect(forwardedUrl("/WEB-INF/view/jsp/main-form.jsp"));
	}

	@Test
	public void when_BreweriesAreNotEmpty_Expect_BeerNameMessageIsCreated() throws Exception {
		// given
		List<BreweryCodes> breweryCodes = new ArrayList<>();

		BreweryCodes breweryCode1 = new BreweryCodes();
		breweryCode1.setBreweryId(1L);

		Beers beers1 = new Beers();
		beers1.setBeerName("Corona");
		Beers beers2 = new Beers();
		beers2.setBeerName("Heineken");
		breweryCode1.setBeers(Arrays.asList(beers1, beers2));

		BreweryCodes breweryCode2 = new BreweryCodes();
		breweryCode2.setBreweryId(2L);

		Beers beers3 = new Beers();
		beers3.setBeerName("Kalnapilis");
		breweryCode2.setBeers(Collections.singletonList(beers3));

		breweryCodes.add(breweryCode1);
		breweryCodes.add(breweryCode2);

		// when
		String message = BeerUtils.getBeerNames(breweryCodes, Arrays.asList(1L, 2L));

		// then
		Assert.assertTrue(message.contains("Corona"));
		Assert.assertTrue(message.contains("Heineken"));
		Assert.assertTrue(message.contains("Kalnapilis"));
	}

	@Test
	public void when_BreweriesAreEmpty_Expect_EmptyMessage() throws Exception {
		// given
		List<BreweryCodes> breweryCodes = new ArrayList<>();

		BreweryCodes breweryCode1 = new BreweryCodes();
		breweryCode1.setBreweryId(1L);

		Beers beers1 = new Beers();
		beers1.setBeerName("Corona");
		breweryCode1.setBeers(Collections.singletonList(beers1));

		BreweryCodes breweryCode2 = new BreweryCodes();
		breweryCode2.setBreweryId(2L);
		Beers beers2 = new Beers();
		beers2.setBeerName("Heineken");
		breweryCode2.setBeers(Collections.singletonList(beers2));

		breweryCodes.add(breweryCode1);
		breweryCodes.add(breweryCode2);

		// when
		String message = BeerUtils.getBeerNames(breweryCodes, Arrays.asList(3L, 4L));

		// then
		Assert.assertFalse(message.contains("Corona"));
		Assert.assertFalse(message.contains("Heineken"));
	}
}
