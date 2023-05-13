package locations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import locations.service.ActivitiesService;

@Configuration
public class ServiceConfig {
    
	@Bean
    public ActivitiesService createActivitiesService(){
        return new ActivitiesService();
    }

}
