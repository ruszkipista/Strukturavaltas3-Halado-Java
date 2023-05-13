package locations;

import java.util.Objects;

public class Location {
    private String name;
    private double latitude;
    private double longitude;
    private double elevationInKm;

    public Location(String name, double latitude, double longitude, double elevationInKm) {
        checkName(name);
        checkLatitude(latitude);
        checkLongitude(longitude);
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevationInKm = elevationInKm;
    }

    private void checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can not be empty!");
        }
    }

    private void checkLatitude(double latitude) {
        if (latitude < -90 || 90 < latitude){
            throw new IllegalArgumentException("Latitude has to be between -90 and +90");
        }
    }

    private void checkLongitude(double longitude) {
        if (longitude < -180 || 180 < longitude){
            throw new IllegalArgumentException("Longitude has to be between -180 and +180");
        }
    }

    public Location(String name, double latitude, double longitude) {
        this(name, latitude, longitude, 0.0);
    }

    public boolean isOnEquator() {
        return latitude == 0.0;
    }

    public boolean isOnPrimeMeridian() {
        return longitude == 0.0;
    }

    public double distanceFrom(Location other) {
        return haversine(latitude, longitude,
                other.latitude, other.longitude,
                elevationInKm, other.elevationInKm);
    }

    private static double haversine(double lat1, double lon1,
            double lat2, double lon2,
            double el1, double el2) {
        final double radiusOfEarthInKm = 6371;
        // convert deltas to radians
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        // apply formulae
        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + (Math.pow(Math.sin(deltaLon / 2), 2)
                        * Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)));
        double c = 2 * Math.asin(Math.sqrt(a));
        double distance = radiusOfEarthInKm * c;
        // adjust distance with elevation
        double height = el1 - el2;
        return Math.sqrt(Math.pow(distance, 2) + Math.pow(height, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Location)) {
            return false;
        }
        Location location = (Location) o;
        return Objects.equals(name, location.name)
                && latitude == location.latitude
                && longitude == location.longitude
                && elevationInKm == location.elevationInKm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, elevationInKm);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}