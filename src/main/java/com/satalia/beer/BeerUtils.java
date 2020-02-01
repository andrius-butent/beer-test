package com.satalia.beer;

import com.satalia.beer.model.BreweryCodes;

public class BeerUtils {

	// radius of the earth
	private static final int R = 6371;

	public static double haversine(BreweryCodes pointA, BreweryCodes pointB) {

		if (pointA == null || pointB == null) {
			return 0;
		}

		double lat1 = pointA.getLatitude();
		double lon1 = pointA.getLongitude();
		double lat2 = pointB.getLatitude();
		double lon2 = pointB.getLongitude();

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
}