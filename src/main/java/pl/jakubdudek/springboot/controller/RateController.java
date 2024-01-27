package pl.jakubdudek.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubdudek.springboot.repository.GroupRepository;
import pl.jakubdudek.springboot.repository.RateRepository;
import pl.jakubdudek.springboot.entity.Group;
import pl.jakubdudek.springboot.entity.Rate;

import java.util.Date;


@RestController
@RequestMapping("/api/rating")
public class RateController {
    @Autowired
    RateRepository rateRepository;

    @Autowired
    GroupRepository groupRepository;

    @PostMapping("/")
    public ResponseEntity<Rate> createRate(@RequestBody Rate rate) {
        Group group = groupRepository.findById(rate.getGroupId()).orElse(null);

        if(group == null) {
            return new ResponseEntity("Group not found", HttpStatus.NOT_FOUND);
        }
        else if(!rate.isValid()) {
            return new ResponseEntity("Group rate must be in range from 0 to 6", HttpStatus.BAD_REQUEST);
        }
        else {
            rate.setGroup(group);
            rate.setDate(new Date());

            Rate savedRate = rateRepository.save(rate);
            return ResponseEntity.ok(savedRate);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRate(@PathVariable Long id) {
        Rate rate = rateRepository.findById(id).orElse(null);

        if(rate == null) {
            return new ResponseEntity("Rate not found", HttpStatus.NOT_FOUND);
        }
        else {
            rateRepository.delete(rate);
            return new ResponseEntity("Rate deleted succesfully", HttpStatus.OK);
        }
    }
}