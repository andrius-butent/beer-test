package com.satalia.beer.controller;

import com.satalia.beer.BeerUtils;
import com.satalia.beer.model.Coordinates;
import com.satalia.beer.service.CoordinatesService;
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
	private CoordinatesService coordinatesService;

	private static final int MAX_DISTANCE = 2000;

	@RequestMapping("/form")
  public String getMainForm(@ModelAttribute("myCoordinates") Coordinates myCoordinates, Model model) {
		model.addAttribute("myCoordinates", new Coordinates());

    return "main-form";
  }

	@RequestMapping("/startBeerTravel")
	public @ResponseBody String startBeerTravel(@ModelAttribute("myCoordinates") Coordinates myCoordinates) {

		List<Coordinates> breweryCoordinatesList = new ArrayList<>();

		for (Coordinates coordinates : coordinatesService.getAllBreweriesCoordinates()) {

			// we calculate distance between home and breweries
			double distance = BeerUtils.haversine(myCoordinates.getLatitude(), myCoordinates.getLongitude(),
				coordinates.getLatitude(), coordinates.getLongitude());

			//collecting breweries that fit in my range
			if (distance <= MAX_DISTANCE / 2d) {
				breweryCoordinatesList.add(coordinates);
			}
		}

		return "TBD";
	}
}
