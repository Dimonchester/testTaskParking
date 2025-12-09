package org.test.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.test.java.dao.CarDAO;
import org.test.java.models.Car;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin(origins = "http://localhost:5173")
public class CarController {
    private final CarDAO carDAO;

    @Autowired
    public CarController(CarDAO carDAO){
        this.carDAO = carDAO;
    }

    @GetMapping
    public List<Car> findCar(@RequestParam(required = false) String carNumber){
        if (carNumber != null && !carNumber.isEmpty()){
            return carDAO.findByNumber(carNumber);
        }
        return carDAO.findAll();
    }

    @PostMapping
    public void saveCar(@RequestBody Car car){
        carDAO.save(car);
    }

    @PutMapping("/{id}")
    public void updateCar(@PathVariable("id") int id, @RequestBody Car car){
        carDAO.update(id, car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable("id") int id){
        carDAO.delete(id);
    }
}
