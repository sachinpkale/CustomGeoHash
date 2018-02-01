package com.swiggy.geohash;

import java.util.List;

public class Example {

    public static void main(String[] args) {
        String geoHash = CustomGeohash.encode(new LatLng(12.906284, 77.59330699999998), 8);
        System.out.println(geoHash);
        LatLng latLng = CustomGeohash.decode(geoHash);
        System.out.println(latLng.getLat() + ", " + latLng.getLng());

        String currentGh = "2747ae4b";
        String[] neighbors = CustomGeohash.adjacent(currentGh);
        for(String s: neighbors) {
            System.out.println(s);
        }
        List<String> geoHashes = Utils.getCellsInHaversineCircleOfRadius(currentGh, 8500);
        System.out.println(geoHashes.size());
    }
}
