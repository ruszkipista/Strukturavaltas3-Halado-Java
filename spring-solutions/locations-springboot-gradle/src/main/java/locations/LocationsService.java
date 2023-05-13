package locations;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationsService {
    @Autowired
    LocationsRepository repo;

    public List<Location> getFavouriteLocations() throws IOException{
        return repo.getFavouriteLocations();
    }
}
