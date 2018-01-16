public class CustomGeohash {

    public static class LatLng {
        Double lat;
        Double lng;

        public LatLng(Double lat, Double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public Double getLng() {
            return lng;
        }
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

    public static void main(String[] args) {
        String geohash = encode(new LatLng(12.906284, 77.59330699999998), 8);
        System.out.println(geohash);
        LatLng latLng = decode(geohash);
        System.out.println(latLng.getLat() + ", " + latLng.getLng());
    }
}
