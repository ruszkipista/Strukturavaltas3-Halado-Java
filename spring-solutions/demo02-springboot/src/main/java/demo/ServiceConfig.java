package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    
    @Bean
    public HiService createHiService() {
        return new HiService();
    }
}
