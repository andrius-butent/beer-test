package com.satalia.beer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/beer")
public class BeerController {

  @RequestMapping("/form")
  public String getMainForm() {
    return "main-form";
  }
}
