package com.informatorio.startups.controller;

import com.informatorio.startups.dto.AddStartupToEventOperation;
import com.informatorio.startups.dto.StartupOperation;
import com.informatorio.startups.entity.Event;
import com.informatorio.startups.entity.Startup;
import com.informatorio.startups.repository.EventRepository;
import com.informatorio.startups.repository.StartupRepository;
import com.informatorio.startups.service.StartupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/startup")
public class StartupController {
    private final StartupRepository startupRepository;
    private final StartupService startupService;
    private final EventRepository eventRepository;

    public StartupController(StartupRepository startupRepository, StartupService startupService,
                             EventRepository eventRepository) {
        this.startupRepository = startupRepository;
        this.startupService = startupService;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllStartups(
            @RequestParam(required = false) Boolean published,
            @RequestParam(required = false) String tag){
        return new ResponseEntity(startupService.getStartupsBy(published,tag), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createStartup(@Valid @RequestBody StartupOperation startupOperation) {
        Startup startup = startupService.createStartup(startupOperation);
        return new ResponseEntity(startupRepository.save(startup), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{startupId}")
    public ResponseEntity<?> addStartupToEvent(
            @PathVariable Long startupId,
            @Valid @RequestBody AddStartupToEventOperation addStartupToEventOperation){
        return new ResponseEntity(startupService.addStartupToEvent(startupId, addStartupToEventOperation),
                HttpStatus.OK);
    }
    @PutMapping(value = "/{startupId}")
    public ResponseEntity<?> updateStartup(
            @PathVariable Long startupId,
            @RequestBody StartupOperation startupOperation){
        return new ResponseEntity(startupService.updateStartup(startupId, startupOperation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{startupId}")
    public ResponseEntity<?> deleteStartup(@PathVariable Long startupId){
        return new ResponseEntity(startupService.deleteStartup(startupId), HttpStatus.OK);
    }
}
