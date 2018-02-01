package com.swiggy.geohash;

import java.util.*;

public class Utils {

    public static List<String> getCellsInHaversineCircleOfRadius(String center, int radius) {
        HaversineDistance haversineDistance = new HaversineDistance();
        Set<String> visited = new HashSet();
        Queue<String> queue = new LinkedList();
        visited.add(center);
        queue.add(center);

        LatLng centerLatLng = CustomGeohash.decode(center);

        while(!queue.isEmpty()) {
            String front = queue.poll();
            String[] neighbours = CustomGeohash.adjacent(front);
            String[] var7 = neighbours;
            int var8 = neighbours.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String neighbour = var7[var9];
                LatLng neighbourLatLng = CustomGeohash.decode(neighbour);
                if (!visited.contains(neighbour) &&
                        haversineDistance.getDistance(centerLatLng.getLat(), centerLatLng.getLng(),
                                neighbourLatLng.getLat(), neighbourLatLng.getLng()) <= radius) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return new ArrayList(visited);
    }
}
