package locations;

public class Location {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    
    public Location(Long id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String name, double latitude, double longitude) {
        this(null, name, latitude, longitude);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Location [id=" + id + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }

}
