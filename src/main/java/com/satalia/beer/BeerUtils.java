package com.satalia.beer;

import com.satalia.beer.model.Beers;
import com.satalia.beer.model.Brewery;
import com.satalia.beer.model.BreweryCodes;

import java.util.List;
import java.util.stream.Collectors;

public class BeerUtils {

	public static final double MAX_DISTANCE = 2000d;

	// radius of the earth
	private static final int R = 6371;

	public static double haversine(BreweryCodes pointA, BreweryCodes pointB) {

		if (pointA == null || pointB == null) {
			return 0;
		}

		return haversine(pointA.getLatitude(), pointA.getLongitude(), pointB.getLatitude(), pointB.getLongitude());
	}

	public static double haversine(double lat1, double lon1, double lat2, double lon2) {

		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formula
		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));

		return R * c;
	}

	public static String generateResultMessage(List<Long> resultOrder, List<Brewery> breweries,
																						 List<BreweryCodes> breweryList, BreweryCodes homeCodes) {
		if (breweries.isEmpty()) {
			return "Need more fuel!!!";
		}

		// creating home brewery
		Brewery homeBrewery = new Brewery();
		homeBrewery.setId(0L);
		homeBrewery.setBreweryName("HOME");

		String resultMessage = "";
		double totalDistance = 0;
		double distance;
		char prefix = '\u2192';

		// I am adding home coordinates
		resultOrder.add(0L);

		resultMessage += "Found " + breweries.size() + " beer factories.<br><br>";

		for (int i = 0; i < resultOrder.size(); i++) {

			Long index = resultOrder.get(i);
			Long previousIndex;

			if (i == 0) {
				previousIndex = resultOrder.get(i);
			} else {
				previousIndex = resultOrder.get(i - 1);
			}

			// collecting breweries, their geo codes and next brewery for calculating distance
			Brewery brewery = breweries.stream().filter(b -> b.getId().equals(index)).findFirst().orElse(homeBrewery);
			BreweryCodes breweryCodes = breweryList.stream().filter(bc -> bc.getBreweryId().equals(index)).findFirst()
				.orElse(homeCodes);
			BreweryCodes previousBreweryCodes = breweryList.stream().filter(bc
				-> bc.getBreweryId().equals(previousIndex)).findFirst().orElse(homeCodes);

			if (i == resultOrder.size() - 1) {
				prefix = '\u2190';
			}

			distance = haversine(breweryCodes, previousBreweryCodes);
			totalDistance += distance;

			resultMessage += prefix + " [" + brewery.getId() + "] " + brewery.getBreweryName() + ": "
				+ breweryCodes.getLatitude() + ", " + breweryCodes.getLongitude() + " distance " + Math.round(distance)
				+ " km<br>";
		}
		resultMessage += "<br><br>Total distance traveled: " + Math.round(totalDistance) + " km";

		return resultMessage;
	}

	public static String getBeerNames(List<BreweryCodes> breweryList, List<Long> breweries) {
		String beerNames = "";
		int count = 0;

		// collecting all beer types from visited breweries
		for (BreweryCodes breweryCodes : breweryList.stream().filter(b
			-> breweries.contains(b.getBreweryId())).collect(Collectors.toList())) {

			for (Beers b : breweryCodes.getBeers()) {
				beerNames += b.getBeerName() + "<br>";
				count++;
			}
		}

		return "Collected " + count + " beer types:<br><br>" + beerNames;
	}

	public static BreweryCodes getBreweryByWeight(List<BreweryCodes> breweryCodes, BreweryCodes currentPoint,
																								List<Long> visited) {

		// filter not visited breweries
		List<BreweryCodes> codes = breweryCodes.stream().filter(code
			-> !visited.contains(code.getBreweryId())).collect(Collectors.toList());

		int index = 0;
		double weight = Double.MIN_VALUE;

		if (!codes.isEmpty()) {
			for (int i = 0; i < codes.size(); i++) {
				double distance = haversine(codes.get(i), currentPoint);
				int beers = codes.get(i).getBeerCount();

				// calculating relation between beer count and distance. We will use biggest value
				if ((beers / distance) > weight) {
					weight = beers / distance;
					index = i;
				}
			}

			return codes.get(index);
		} else {
			return null;
		}
	}
}