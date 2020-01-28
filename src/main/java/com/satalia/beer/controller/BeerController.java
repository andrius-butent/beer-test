package com.satalia.beer.controller;

import com.satalia.beer.model.Coordinates;
import com.satalia.beer.service.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/beer")
public class BeerController {

	@Autowired
	private CoordinatesService coordinatesService;

	@RequestMapping("/form")
  public String getMainForm(@ModelAttribute("coordinates") Coordinates coordinates, Model model) {
		model.addAttribute("coordinates", new Coordinates());

    return "main-form";
  }

	@RequestMapping("/startBeerTravel")
	public @ResponseBody String startBeerTravel(@ModelAttribute("coordinates") Coordinates coordinates) {
		return "TBD";
	}
}
