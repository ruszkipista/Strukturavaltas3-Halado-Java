package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    HelloService service;
    
    @GetMapping("/ho")
    public String sayHello(){
        return service.sayHello();
    }
}
