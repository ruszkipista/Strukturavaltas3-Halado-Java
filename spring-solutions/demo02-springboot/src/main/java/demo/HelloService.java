package demo;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String sayHello(){
        return "Hello Boot! (service)";
    }
}
