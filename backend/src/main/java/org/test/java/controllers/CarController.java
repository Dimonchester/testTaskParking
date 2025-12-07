package org.test.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.test.java.dao.CarDAO;
import org.test.java.models.Car;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CarController {
    private final CarDAO carDAO;

    @Autowired
    public CarController(CarDAO carDAO){
        this.carDAO = carDAO;
    }

    @GetMapping("/cars")
    public List<Car> index(){
        return carDAO.index();
    }
}
