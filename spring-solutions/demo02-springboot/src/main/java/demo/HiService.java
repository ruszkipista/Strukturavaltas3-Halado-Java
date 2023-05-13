package demo;

//no @Service annotation! - configured in ServiceConfig.java
public class HiService {

    public String sayHi(){
        return "Hi Spring! (service)";
    }
}
