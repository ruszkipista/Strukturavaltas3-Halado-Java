package locations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    
    @Bean
    public ActivitiesService createActivitiesService(){
        return new ActivitiesService();
    }
}
