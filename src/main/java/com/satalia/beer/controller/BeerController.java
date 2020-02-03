package com.satalia.beer.controller;

import com.satalia.beer.BeerUtils;
import com.satalia.beer.model.Beers;
import com.satalia.beer.model.Brewery;
import com.satalia.beer.model.BreweryCodes;
import com.satalia.beer.model.TravelHistory;
import com.satalia.beer.service.BeersService;
import com.satalia.beer.service.BreweryCodesService;
import com.satalia.beer.service.BreweryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/beer")
public class BeerController {

	@Autowired
	private BreweryCodesService breweryCodesService;

	@Autowired
	private BeersService beersService;

	@Autowired
	private  BreweryService breweryService;

	private List<Long> result = new ArrayList<>();

	@RequestMapping("/form")
  public String getMainForm(@ModelAttribute("myCoordinates") BreweryCodes myCoordinates, Model model) {
		model.addAttribute("myCoordinates", new BreweryCodes());

    return "main-form";
  }

	@RequestMapping("/startBeerTravel")
	public String startBeerTravel(@ModelAttribute("myCoordinates") BreweryCodes myCoordinates, Model model) {

		long timeBefore = System.currentTimeMillis();

		List<BreweryCodes> breweryList = new ArrayList<>();
		for (BreweryCodes brewery : breweryCodesService.getAllBreweryCodes()) {

			// we calculate distance between home and breweries
			double distanceToHome = BeerUtils.haversine(myCoordinates, brewery);

			// collecting breweries that fit by the distance
			if (distanceToHome <= BeerUtils.MAX_DISTANCE / 2d) {
				breweryList.add(brewery);
			}
		}

		// adding beers to their breweries
		List<Beers> beers = beersService.getAllBeers();
		for (BreweryCodes bc : breweryList) {
			for (Beers beer : beers) {
				if (beer.getBreweryId().equals(bc.getBreweryId())) {
					bc.addBeer(beer);
				}
			}
		}

		int totalBeerCount = 0;

		// creating initial travel point
		TravelHistory home = new TravelHistory (myCoordinates.getLatitude(), myCoordinates.getLongitude(), 0d, 0);
		home.setParents(Collections.singletonList(0L));

		// stack for calculations
		Stack<TravelHistory> stack = new Stack<>();
		stack.add(home);

		// the loop will work until all possible breweries will be visited
		while (!stack.isEmpty()) {

			TravelHistory currentPoint = stack.pop();
			List<Long> breweryIds = currentPoint.getParents();

			for (BreweryCodes nextBrewery : breweryList) {

				if (breweryIds.contains(nextBrewery.getBreweryId())) {
					continue;
				}

				double distanceToHome = BeerUtils.haversine(myCoordinates, nextBrewery);
				double distanceBetween = BeerUtils.haversine(currentPoint, nextBrewery) + currentPoint.getTraveledDistance();

				int beerCount = currentPoint.getCollectedBeers() + nextBrewery.getBeerCount();

				if ((distanceBetween + distanceToHome) <= BeerUtils.MAX_DISTANCE && totalBeerCount <= beerCount) {

					totalBeerCount = beerCount;

					// new travel history point
					TravelHistory travel = new TravelHistory(nextBrewery.getLatitude(), nextBrewery.getLongitude(),
						distanceBetween, totalBeerCount);
					travel.setParents(new ArrayList<>(currentPoint.getParents()));
					travel.getParents().add(nextBrewery.getBreweryId());

					stack.push(travel);

					result = new ArrayList<>(travel.getParents());
				}
			}
		}

		// selecting names of breweries
		List<Brewery> breweries = breweryService.getAllBreweriesById(result);

		model.addAttribute("resultMessage", BeerUtils.generateResultMessage(result, breweries, breweryList,
			myCoordinates));
		model.addAttribute("beerNames", BeerUtils.getBeerNames(breweryList, result));
		model.addAttribute("time", System.currentTimeMillis() - timeBefore);

		return "result-page";
	}

	@RequestMapping("/goBack")
	public String goBack() {
		return "redirect:form";
	}
}
