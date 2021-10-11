package com.benze.assignment.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.benze.assignment.entity.Car;
import com.benze.assignment.entity.ChargingStation;
import com.benze.assignment.entity.Distance;
import com.benze.assignment.entity.Errors;
import com.benze.assignment.entity.Response;
import com.benze.assignment.entity.Station;
import com.benze.assignment.entity.SuccessResponse;
import com.google.gson.Gson;

@Service
public class CarService {

	String CHARGE_LEVEL = "https://restmock.techgig.com/merc/charge_level";
	String CHARGEING_STATION = "https://restmock.techgig.com/merc/charging_stations";
	String DISTANCE = "https://restmock.techgig.com/merc/distance";

	private RestTemplate restTemplate = new RestTemplate();

	private Car findChargeLevel(String vin) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		Map<String, Object> map = new HashMap<>();
		map.put("vin", vin);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(CHARGE_LEVEL, entity, String.class);

		Car car = new Gson().fromJson(response.getBody(), Car.class);

		System.out.println("Car : " + response.getBody());

		return car;
	}

	private Distance findDistance(String source, String destination) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		Map<String, Object> map = new HashMap<>();
		map.put("source", source);
		map.put("destination", destination);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(DISTANCE, entity, String.class);

		Distance distance = new Gson().fromJson(response.getBody(), Distance.class);

		System.out.println("Distance between Stations : " + response.getBody());

		return distance;
	}

	private ChargingStation findChargingStation(String source, String destination) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		Map<String, Object> map = new HashMap<>();
		map.put("source", source);
		map.put("destination", destination);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(CHARGEING_STATION, entity, String.class);

		System.out.println("Charging Station : " + response.getBody());

		ChargingStation station = new Gson().fromJson(response.getBody(), ChargingStation.class);

		return station;

	}

	public Response checkTrip(String vin, String source, String destination) {

		Car car = findChargeLevel(vin);

		Distance distance = findDistance(source, destination);

		ChargingStation chargingStation = findChargingStation(source, destination);

		Errors error = new Errors();

		if (null != source && null != destination && ("Home".equalsIgnoreCase(source) && !"Power Plant".equalsIgnoreCase(destination))) {

			if ((car == null || distance == null || chargingStation == null)
				|| ((null != car.getError() && "null" != car.getError() && car.getError().contains("Invalid"))
					|| (null != distance.getError() && "null" != distance.getError() && distance.getError().contains("Invalid"))
						|| (null != chargingStation.getError() && "null" != chargingStation.getError()
							&& chargingStation.getError().contains("Invalid")))) {

				return prepareTechicalErrorResponse();
			}
		}

		return checkForTravel(car, distance, chargingStation, error);
	}

	private SuccessResponse checkForTravel(Car car, Distance distance, ChargingStation chargingStation, Errors error) {

		SuccessResponse response = new SuccessResponse();

		List<Station> stationLst = new ArrayList<>();

		if (null != chargingStation.getChargingStations() && chargingStation.getChargingStations().size() > 0) {

			stationLst = chargingStation.getChargingStations().stream()
					.sorted((o1, o2) -> ((Integer) o1.getDistance()).compareTo(o2.getDistance()))
					.collect(Collectors.toList());
		}

		int startLevel = car.getCurrentChargeLevel();

		int target = distance.getDistance();

		boolean isChargingRequired = true;

		List<String> stations = new ArrayList<>();

		if (stationLst.size() > 0) {
			stations = findStations(startLevel, target, stationLst);

		} else {

			if (startLevel > target) {
				isChargingRequired = false;
			}
		}

		// int currentLevel = car.getCurrentChargeLevel();
		// int destinationDistance = distance.getDistance();
		// int newLevelAfterLeavingStation = 0;
		// int newLevelAfterReachingStation = 0;
		// int prevDistanceFromSource = 0;
		//
		// int x = 0;
		//
		// if (stationLst.size() > 0) {
		//
		// for (Station st : stationLst) {
		//
		// if (currentLevel > destinationDistance) {
		// isChargingRequired = false;
		// break;
		// } else if (x <= destinationDistance && (currentLevel - st.getDistance()) >=
		// 0) {
		//
		// // 10 - 0 = 10 :: 25 - 10 = 15
		// // 10 - 0 = 10
		// prevDistanceFromSource = st.getDistance() - prevDistanceFromSource;
		//
		// // 17 - 10 = 7 :: 27 - 15 = 12
		// // 2 - 10 = -8
		// newLevelAfterReachingStation = currentLevel - prevDistanceFromSource;
		//
		// // 7 + 20 = 27 :: 12 + 15 = 27
		// // cannot reach
		// newLevelAfterLeavingStation = newLevelAfterReachingStation + st.getLimit();
		//
		// // 27 :: 27
		// // cannot reach
		// currentLevel = newLevelAfterLeavingStation;
		//
		// // 0 + 27 = 27 :: 27 + 27 = 54
		//
		// x = x + currentLevel;
		//
		// stations.add(st.getName());
		// }
		//
		// }
		//
		// } else {
		//
		// if (currentLevel > 0) {
		// isChargingRequired = false;
		// }
		// }

		// stations.stream().forEach(a -> System.out.print(a+" "));

		if (startLevel > target && (null == stations || stations.size() == 0)) {
			isChargingRequired = false;
		}
		
		if (isChargingRequired && (null == stations || stations.size() == 0)) {

			List<Errors> errorList = new ArrayList<>();
			Errors errors = new Errors();

			errors.setId(8888);
			errors.setDescription("Unable to reach the destination with the current fuel level");

			errorList.add(errors);

			response.setErrors(errorList);
		}

		if (null != stations && stations.size() > 0) {
			response.setChargingStations(stations);
		}
		response.setTransactionId(new Date().getTime());
		response.setVin(car.getVin());
		response.setSource(distance.getSource());
		response.setDestination(distance.getDestination());
		response.setDistance(distance.getDistance());
		response.setCurrentChargeLevel(car.getCurrentChargeLevel());
		response.setChargingRequired(isChargingRequired);

		return response;
	}

	private List<String> findStations(int startFuel, int target, List<Station> stationLst) {

		int stops = 0;
		int station = 0;

		List<String> stopLst = new ArrayList<>();

		PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

		while (startFuel < target) {

			while (station < stationLst.size() && startFuel >= stationLst.get(station).getDistance()) {
				pq.add(stationLst.get(station).getLimit());
				station++;
			}
			if (pq.size() <= 0)
				return null;

			startFuel += pq.remove();

			stopLst.add(stationLst.get(stops).getName());

			stops++;
		}

		stopLst.stream().forEach(x -> System.out.print(x + " "));

		return stopLst;

	}

	private Response prepareTechicalErrorResponse() {

		Response response = new Response();
		Errors errors = new Errors();

		List<Errors> errorList = new ArrayList<>();

		errors.setId(9999);
		errors.setDescription("Technical Exception");

		errorList.add(errors);

		response.setTransactionId(new Date().getTime());
		response.setErrors(errorList);

		return response;
	}

}
