package com.base.servicer1.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/r1/api/base")
public class BaseController {

    private int counter;

    @GetMapping("/check")
    public String check() {
        counter++;
        return String.format("Base controller. Response number: %d", counter);
    }
}