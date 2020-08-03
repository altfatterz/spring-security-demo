package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

}


@RestController
class GreetingController {

    @GetMapping("/api/greeting")
    String greet(@RequestParam(defaultValue = "World") String name) {
        return "Hello " + name;
    }

    @GetMapping("/")
    String index() {
        return "Go on, nothing to see here";
    }


}