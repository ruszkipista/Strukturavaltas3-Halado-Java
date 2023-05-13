package locations;

public class LocationParser {

    public Location parse(String text) {
        String[] fields = text.split(",");
        String name = fields[0];
        double latitude = Double.parseDouble(fields[1]);
        double longitude = Double.parseDouble(fields[2]);
        return new Location(name, latitude, longitude);
    }
}
