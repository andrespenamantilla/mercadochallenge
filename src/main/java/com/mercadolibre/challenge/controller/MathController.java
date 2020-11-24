package com.mercadolibre.challenge.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/math/")
public class MathController {
    @GetMapping("/add/{number1}/{number2}")
    public Integer add(@PathVariable Integer number1, @PathVariable Integer number2) throws Exception {
        return number1 + number2;
    }
    @PostMapping("/add")
    public Integer add(@RequestBody Add add)throws Exception {
        return add.number1 + add.number2;
    }
    /**
     * just for a quick example fields are public
     */
    class Add {
        public Integer number1;
        public Integer number2;
    }
}