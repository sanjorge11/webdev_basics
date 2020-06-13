package com.Multithreading.MutuallyExclusive.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Multithreading.MutuallyExclusive.services.CalculationService;

@RestController
@RequestMapping(value = "/calculate")
public class CalculationContoller {
	
	@Autowired
	private CalculationService calculationService;

	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public int calculate(@RequestParam int num) {
		return calculationService.calculate(num);
	}

}
