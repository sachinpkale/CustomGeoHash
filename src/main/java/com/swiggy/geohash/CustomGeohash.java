package com.swiggy.geohash;

public class CustomGeohash {

    private static String leftpad(String s, int num) {
        String r = "";
        for(int i = 0; i < num; i++) {
            r += "0";
        }
        return r + s;
    }

    private static String interleave(String lngs, String lats) {
        if(lngs.length() < lats.length()) {
            lngs = leftpad(lngs, lats.length() - lngs.length());
        }
        if(lats.length() < lngs.length()) {
            lats = leftpad(lats, lngs.length() - lats.length());
        }
        String s = "";
        for(int i = 0; i < lngs.length(); i++) {
            s += lngs.charAt(i);
            s += lats.charAt(i);
        }
        return s;
    }

    private static String binaryToHex(String binaryString) {
        return Long.toHexString(Long.parseLong(binaryString, 2));
    }

    public static String[] adjacent(String geohash) {
        String value = Long.toBinaryString(Long.decode("0X" + geohash));
        String lats = "";
        String lngs = "";
        for(int i = 0; i < value.length(); i++) {
            if(i%2 == 0) {
                lngs += value.charAt(i);
            } else {
                lats += value.charAt(i);
            }
        }
        Long lngNum = Long.parseLong(lngs, 2);
        Long latNum = Long.parseLong(lats, 2);
        String leftLng = Long.toBinaryString(lngNum - 1);
        String rightLng = Long.toBinaryString(lngNum + 1);
        String upLat = Long.toBinaryString(latNum + 1);
        String downLat = Long.toBinaryString(latNum - 1);
        String[] neighbors = {binaryToHex(interleave(leftLng, lats)), binaryToHex(interleave(rightLng, lats)),
                binaryToHex(interleave(lngs, upLat)), binaryToHex(interleave(lngs, downLat))};
        return neighbors;
    }

    public static LatLng decode(String geohash) {
        String value = Long.toBinaryString(Long.decode("0X" + geohash));
        int leftPad = 4 - (value.length() % 4);
        if(leftPad > 0) {
            for(int i = 0; i < leftPad; i++) {
               value = "0" + value;
            }
        }
        double minLat = 06,  maxLat = 36;
        double minLng = 68, maxLng = 98;
        for(int i = 0; i < value.length(); i++) {
            if (i % 2 == 0) {
                double midpoint = (minLng + maxLng) / 2;
                if (value.charAt(i) == '1') {
                    minLng = midpoint;
                } else {
                    maxLng = midpoint;
                }
            } else {
                double midpoint = (minLat + maxLat) / 2;
                if (value.charAt(i) == '1') {
                    minLat = midpoint;
                } else {
                    maxLat = midpoint;
                }
            }
        }
        return new LatLng((minLat + maxLat) / 2, (minLng + maxLng) / 2);
    }

    public static String encode(LatLng latlng, int precision) {
        double lat = latlng.getLat();
        double lng = latlng.getLng();
        double minLat = 06,  maxLat = 36;
        double minLng = 68, maxLng = 98;
        int bits = precision * 4;
        long result = 0;
        for (int i = 0; i < bits; ++i) {
            if (i % 2 == 0) {
                double midpoint = (minLng + maxLng) / 2;
                if (lng < midpoint) {
                    result <<= 1;
                    maxLng = midpoint;
                } else {
                    result = result << 1 | 1;
                    minLng = midpoint;
                }
            } else {
                double midpoint = (minLat + maxLat) / 2;
                if (lat < midpoint) {
                    result <<= 1;
                    maxLat = midpoint;
                } else {
                    result = result << 1 | 1;
                    minLat = midpoint;
                }
            }
        }
        return Long.toHexString(result);
    }
}
