package com.haubmannlucas.barbershop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MainApplication {

    @RequestMapping("/")
    String Home (){
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
