package com.swiggy.geohash;

public class HaversineDistance {
    private static final double R = 6372.8D;

    public int getDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.pow(Math.sin(dLat / 2.0D), 2.0D) + Math.pow(Math.sin(dLon / 2.0D), 2.0D) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2.0D * Math.asin(Math.sqrt(a));
        return (int)Math.round(6372.8D * c * 1000.0D);
    }
}
