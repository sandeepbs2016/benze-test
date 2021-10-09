package com.benze.assignment.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.benze.assignment.entity.Response;
import com.benze.assignment.service.CarService;


@RestController
public class CarController {


	@Autowired
	private CarService carService;
	
	@PostMapping("/test")                
	public String test() {
		return "hi";
	}
	
	@PostMapping("/check_charge_stations")
	public @ResponseBody Response testController(@RequestBody Map<String, String> requestParams) {
		
		System.out.println("VIN : "+requestParams.get("vin"));
		
		String vin = requestParams.get("vin");
		
		String source = requestParams.get("source");
		String destination = requestParams.get("destination");

		return carService.checkTrip(vin, source, destination);

	}
}
