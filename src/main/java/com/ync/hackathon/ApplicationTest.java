package com.ync.hackathon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationTest {

    @GetMapping("/api")
    public String test() {
        return "Hello World!!";
    }

    

}
