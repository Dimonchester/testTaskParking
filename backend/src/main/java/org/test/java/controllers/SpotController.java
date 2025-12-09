package org.test.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.test.java.dao.SpotDAO;
import org.test.java.models.Spot;

import java.util.List;

@RestController
@RequestMapping("/api/spots")
@CrossOrigin(origins = "http://localhost:5173")
public class SpotController {
    private final SpotDAO spotDAO;

    @Autowired
    public SpotController(SpotDAO spotDAO){
        this.spotDAO = spotDAO;
    }

    @GetMapping
    public List<Spot> findAll(){
        return spotDAO.findAll();
    }

    @PostMapping
    public void saveSpot(@RequestBody Spot spot) {
        spotDAO.save(spot);
    }

    @PutMapping("/{id}")
    public void updateSpot(@PathVariable("id") int id, @RequestBody Spot spot){
        spotDAO.update(id, spot);
    }

    @DeleteMapping("/{id}")
    public void deleteSpot(@PathVariable("id") int id){
        spotDAO.delete(id);
    }
}
