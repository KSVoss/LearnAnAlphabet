package ksvoss.backend.learnanalphabet.Hello;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping(path="/hello")
    String sayHello(){
        return "Hello";
    }
}
