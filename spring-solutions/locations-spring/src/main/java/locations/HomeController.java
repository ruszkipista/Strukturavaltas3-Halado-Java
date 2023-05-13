package locations;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() throws IOException {
        ClassPathResource resource = new ClassPathResource("/index.html");
        return resource.getContentAsString(Charset.defaultCharset());
    }

}