package com.example.domainmodelopdrachtspringbootles11.controller;

import com.example.domainmodelopdrachtspringbootles11.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TelevisionController {
    ArrayList<String >televisionDataBase = new ArrayList<>();

    @GetMapping("television")
    public ResponseEntity<ArrayList<String>> getAllTelevisions() {
        televisionDataBase.add("Sony Bravia");
        televisionDataBase.add("Samsung QLED");
        televisionDataBase.add("LG OLED");
        return new ResponseEntity<>(televisionDataBase, HttpStatus.OK);
    }

    @GetMapping("television/{id}")
    public ResponseEntity<String> getTelevisionById(@PathVariable int id) {
        String television;
        if (id != 1 && id != 2 && id != 3) {
            throw new RecordNotFoundException("Opgegeven ID kan niet gevonden worden");
        } else {
            if (id == 1) {
                television = "Sony Bravia";
            } else if (id == 2) {
                television = "Samsung QLED";
            } else {
                television= "LG OLED";
            }
            return new ResponseEntity<>(television, HttpStatus.OK);
        }
    }

    @PutMapping("television/{id}")
    public ResponseEntity<String> updateTelevision(@PathVariable int id, @RequestBody String television) {
        if (id >= televisionDataBase.size() || id < 0) {
            return new ResponseEntity<>("Invalid index", HttpStatus.BAD_REQUEST);
        } else {
            televisionDataBase.set(id, television);
            return new ResponseEntity<>("Television corrected successfully", HttpStatus.OK);
        }
    }

    @PostMapping("television")
    public ResponseEntity<String> addTelevision(@RequestBody String television) {
            televisionDataBase.add(television);
            return new ResponseEntity<>("Television added successfully", HttpStatus.CREATED);
        }

    @DeleteMapping("television/{id}")
    public ResponseEntity<String> deleteTelevision(@PathVariable int id) {
        if (id >= televisionDataBase.size() || id < 0) {
            return new ResponseEntity<>("Invalid index", HttpStatus.BAD_REQUEST);
        }
        else {
            televisionDataBase.set(id, null);
            return new ResponseEntity<>("Television deleted successfully", HttpStatus.OK);
        }

    }

}
