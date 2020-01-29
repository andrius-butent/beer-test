package com.satalia.beer;

import com.satalia.beer.model.Coordinates;

import java.util.List;
import java.util.Map;

public class BeerUtils {

	// radius of the earth
	private static final int R = 6371;

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

	public static double[][] getDistanceMatrix(List<Coordinates> breweryCoordinatesList, Map<Integer, Long> idMap) {

		if (breweryCoordinatesList != null && !breweryCoordinatesList.isEmpty()) {
			double[][] distanceMatrix = new double[breweryCoordinatesList.size()][breweryCoordinatesList.size()];
			Coordinates pointA;
			Coordinates pointB;

			// creating distance matrix across all breweries
			for (int i = 0; i < breweryCoordinatesList.size(); i++) {
				pointA = breweryCoordinatesList.get(i);
				idMap.put(i, pointA.getId());

				for (int j = 0; j < breweryCoordinatesList.size(); j++) {
					pointB = breweryCoordinatesList.get(j);

					distanceMatrix[i][j] = haversine(pointA.getLatitude(), pointA.getLongitude(), pointB.getLatitude(),
						pointB.getLongitude());
				}
			}

			return distanceMatrix;
		}

		return new double[0][];
	}
}