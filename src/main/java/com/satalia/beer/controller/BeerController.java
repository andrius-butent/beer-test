package com.satalia.beer.controller;

import com.satalia.beer.BeerUtils;
import com.satalia.beer.model.Beers;
import com.satalia.beer.model.Brewery;
import com.satalia.beer.model.BreweryCodes;
import com.satalia.beer.service.BeersService;
import com.satalia.beer.service.BreweryCodesService;
import com.satalia.beer.service.BreweryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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

	@RequestMapping("/form")
  public String getMainForm(@ModelAttribute("myCoordinates") BreweryCodes myCoordinates, Model model) {
		model.addAttribute("myCoordinates", new BreweryCodes());

    return "main-form";
  }

	@RequestMapping("/startBeerTravel")
	public String startBeerTravel(@Valid @ModelAttribute("myCoordinates") BreweryCodes myCoordinates,
																BindingResult bindingResult,  Model model) {

		if (bindingResult.hasErrors()) {
			return "main-form";
		}
		// time when program started
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

		// check if we found any breweries
		if (breweryList.isEmpty()) {
			model.addAttribute("resultMessage", "<h3>Need more fuel!!!</h3>");
			model.addAttribute("time", System.currentTimeMillis() - timeBefore);

			return "result-page";
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

		// stack for calculations
		Stack<BreweryCodes> stack = new Stack<>();
		stack.add(myCoordinates);

		List<Long> result = new ArrayList<>();
		result.add(0L);

		// this contains visited breweries
		List<Long> visited = new ArrayList<>();

		double totalDistance = 0;

		while (!stack.isEmpty()) {

			BreweryCodes current = stack.pop();
			BreweryCodes nextBrewery = BeerUtils.getBreweryByWeight(breweryList, current, visited);

			if (nextBrewery == null) {
				break;
			}

			double distanceToHome = BeerUtils.haversine(myCoordinates, nextBrewery);
			double distanceBetween = BeerUtils.haversine(current, nextBrewery) + totalDistance;

			if ((distanceBetween + distanceToHome) <= BeerUtils.MAX_DISTANCE) {

				totalDistance = distanceBetween;
				result.add(nextBrewery.getBreweryId());
				visited.add(nextBrewery.getBreweryId());

				stack.push(nextBrewery);
			} else {

				// if we don't find the biggest weight and have fuel left then try again
				visited.add(nextBrewery.getBreweryId());
				stack.push(current);
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
		// go back to main form
		return "redirect:form";
	}
}
