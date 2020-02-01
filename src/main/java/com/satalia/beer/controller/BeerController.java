package com.satalia.beer.controller;

import com.satalia.beer.BeerUtils;
import com.satalia.beer.model.BreweryCodes;
import com.satalia.beer.service.BreweryCodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/beer")
public class BeerController {

	@Autowired
	private BreweryCodesService breweryCodesService;

	private static final int MAX_DISTANCE = 2000;

	@RequestMapping("/form")
  public String getMainForm(@ModelAttribute("myCoordinates") BreweryCodes myCoordinates, Model model) {
		model.addAttribute("myCoordinates", new BreweryCodes());

    return "main-form";
  }

	@RequestMapping("/startBeerTravel")
	public @ResponseBody String startBeerTravel(@ModelAttribute("myCoordinates") BreweryCodes myCoordinates) {

		List<BreweryCodes> breweryList = new ArrayList<>();

		myCoordinates.setId(0L);
		breweryList.add(myCoordinates);

		for (BreweryCodes brewery : breweryCodesService.getAllBreweryCodes()) {

			// we calculate distance between home and breweries
			double distance = BeerUtils.haversine(myCoordinates, brewery);

			// collecting breweries that fit by the distance
			if (distance <= MAX_DISTANCE / 2d) {
				breweryList.add(brewery);
			}
		}

		return "TBD";
	}
}
