package locations;

import java.util.Optional;

public class LocationsService {
    LocationsRepository repo;

    public LocationsService(LocationsRepository repo) {
        this.repo = repo;
    }

    public Optional<Double> calculateDistance(String name1, String name2){
        Optional<Location> location1 = repo.findByName(name1);
        if (location1.isEmpty())
            return Optional.empty();

        Optional<Location> location2 = repo.findByName(name2);
        if (location2.isEmpty())
            return Optional.empty();

        double distance = location1.get().distanceFrom(location2.get());
        return Optional.of(distance);
    }

    public boolean isOnNorthernHemisphere(String name){
        Optional<Double> latitude = repo.findLatitudeByName(name);
        return 0.0 < latitude.orElseThrow(() -> new IllegalArgumentException("No such location name!"));
    }

}
