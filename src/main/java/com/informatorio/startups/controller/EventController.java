package com.informatorio.startups.controller;

import com.informatorio.startups.dto.EventOperation;
import com.informatorio.startups.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/event")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService){
        this.eventService = eventService;
    }
    
    @GetMapping
    public ResponseEntity<?> getEvents(){
        return new ResponseEntity(eventService.getAllEvents(), HttpStatus.OK);
    }

    @GetMapping(value = "/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable Long eventId){
        return new ResponseEntity(eventService.calculateVotes(eventId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventOperation eventOperation){
        return new ResponseEntity<>(eventService.createEvent(eventOperation), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{eventId}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long eventId,
            @RequestBody EventOperation eventOperation){
        return new ResponseEntity<>(eventService.updateEvent(eventId, eventOperation), HttpStatus.OK);
    }

    @PostMapping(value = "/{eventId}/{startupId}")
    public ResponseEntity<?> addStartup(
            @PathVariable Long eventId,
            @PathVariable Long startupId){
        return new ResponseEntity<>(eventService.addStartup(eventId, startupId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId){
        return new ResponseEntity<>(eventService.deleteEvent(eventId), HttpStatus.OK);
    }
}
