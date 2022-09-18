package com.seogineer.educoncertreinventthewheel.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WheelController {
    @GetMapping(name = "/name/{id}")
    public void get(@PathVariable String id) {

    }
}
