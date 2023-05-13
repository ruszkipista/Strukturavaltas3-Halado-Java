package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    HiService service;
    
    @GetMapping("/hi")
    public String sayHi(){
        return service.sayHi();
    }
}
