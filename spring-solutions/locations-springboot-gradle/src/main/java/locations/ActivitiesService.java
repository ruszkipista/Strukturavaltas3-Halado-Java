package locations;

import java.io.IOException;
import java.util.Arrays;

// no @Service annotation! - configured in ServiceConfig.java
public class ActivitiesService {

    public String getFavouriteActivities() throws IOException{
        return Arrays.toString(new String[] {"Running","Swimming","Yoga","Reading"});
    }
}
