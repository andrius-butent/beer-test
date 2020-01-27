package com.satalia.beer.controller;

import com.satalia.beer.model.Coordinates;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/beer")
public class BeerController {

  @RequestMapping("/form")
  public String getMainForm(@ModelAttribute("coordinates") Coordinates coordinates, Model model) {
		model.addAttribute("coordinates", new Coordinates());

    return "main-form";
  }
}
