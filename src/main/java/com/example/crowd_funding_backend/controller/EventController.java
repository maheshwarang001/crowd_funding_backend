package com.example.crowd_funding_backend.controller;

import com.example.crowd_funding_backend.model.event.Event;
import com.example.crowd_funding_backend.model.event.EventQuery;
import com.example.crowd_funding_backend.repository.EventDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventController {

    @Autowired
    EventDB eventRepo;


    @PostMapping("/event-register")
    public ResponseEntity<HttpStatus> createEvent(@RequestBody EventQuery data){
        try {

            Event event = new Event();
            event.setOwner(data.getOwner());
            event.setEventTitle(data.getEventTitle());
            event.setEventDesc(data.getEventDesc());
            event.setImage(data.getImage());
            event.setTag(data.getTag());
            event.setTargetAmount(data.getTargetAmount());
            event.setCreatedAt(LocalDateTime.now());
            event.setWithdraw(false);
            event.setOpen(true);

            eventRepo.save(event);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }catch (Exception e){

            log.error(e.toString());
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/created-by")
    public ResponseEntity<List<Event>> getPersonalEventList(@RequestParam("ownerID") UUID userID) {
        if (userID == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Event> events = eventRepo.findByOwner(userID).orElse(Collections.emptyList());
        Collections.reverse(events);
        return ResponseEntity.ok(events);
    }


}
