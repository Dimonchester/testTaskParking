package org.test.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.test.java.dao.OwnerDAO;
import org.test.java.models.Owner;

import java.util.List;

@RestController
@RequestMapping("api/owners")
@CrossOrigin(origins = "http://localhost:5173")
public class OwnerController {
    private final OwnerDAO ownerDAO;

    @Autowired
    public OwnerController(OwnerDAO ownerDAO){
        this.ownerDAO = ownerDAO;
    }

    @GetMapping
    public List<Owner> find(@RequestParam(required = false) String query){
        if (query != null && !query.isEmpty()){
            return ownerDAO.findByQuery(query);
        }
        return ownerDAO.findAll();
    }

    @PostMapping
    public void saveOwner(@RequestBody Owner owner){
        ownerDAO.save(owner);
    }

    @PutMapping("/{id}")
    public void updateOwner(@PathVariable("id") int id,
                            @RequestBody Owner owner){
        ownerDAO.update(id, owner);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable("id") int id){
        ownerDAO.delete(id);
    }
}
