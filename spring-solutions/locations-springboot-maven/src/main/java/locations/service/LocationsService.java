package locations.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import locations.model.CoordinateLimit;
import locations.model.Location;
import locations.model.LocationCreateCommand;
import locations.model.LocationDto;
import locations.model.LocationMapper;
import locations.model.LocationUpdateCommand;
import locations.repository.LocationsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LocationsService {
    private LocationsRepository repo;
    private LocationMapper locationMapper;

    public List<LocationDto> getLocations(Optional<String> namePrefix) {
        if (namePrefix.isEmpty()){
            return this.locationMapper.toDto(repo.findAll());
        }
        return this.locationMapper.toDto(repo.findAllByPrefix(namePrefix.get()));
    }

    public LocationDto getLocationById(long locationId) {
        return this.locationMapper.toDto(repo.findById(locationId)
                                             .orElseThrow(()->new LocationNotFoundException("Unable to find location " + locationId)));
    }
    
    public List<LocationDto> getLocationsWithinLimits(CoordinateLimit limit) {
        List<Location> locations = repo.findAll().stream()
                .filter(l -> isLocationWithinLimits(l, limit))
                .toList();
        return this.locationMapper.toDto(locations);
    }

    private boolean isLocationWithinLimits(Location location, CoordinateLimit limit) {
        return limit.getMinLat() <= location.getLatitude() && location.getLatitude() <= limit.getMaxLat()
            && limit.getMinLon() <= location.getLongitude() && location.getLongitude() <= limit.getMaxLon();
    }

    public LocationDto createLocation(LocationCreateCommand command) {
        Location location = repo.save(this.locationMapper.fromCreateCommand(command));
        return this.locationMapper.toDto(location);
    }

    public LocationDto updateLocationById(long locationId, LocationUpdateCommand locationUpdateCommand) {
        Location location;
        try {
            location = repo.findById(locationId)
                           .orElseThrow(()->new IllegalArgumentException("Location not found: "+locationId));
        } catch (Exception e) {
            throw new LocationNotFoundException("Unable to find location to update " + locationId, e);
        }
        Location locationUpdated = this.locationMapper.fromUpdateCommand(locationUpdateCommand, location);
        locationUpdated = repo.save(locationUpdated);
        return this.locationMapper.toDto(locationUpdated);
    }

    public void removeLocationById(long locationId) {
        repo.deleteById(locationId);
    }
}
