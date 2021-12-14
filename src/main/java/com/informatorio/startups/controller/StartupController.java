package com.informatorio.startups.controller;

import com.informatorio.startups.dto.StartupOperation;
import com.informatorio.startups.entity.Startup;
import com.informatorio.startups.repository.StartupRepository;
import com.informatorio.startups.service.StartupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/startup")
public class StartupController {
    private final StartupRepository startupRepository;
    private final StartupService startupService;

    public StartupController(StartupRepository startupRepository, StartupService startupService) {
        this.startupRepository = startupRepository;
        this.startupService = startupService;
    }

    @GetMapping
    public ResponseEntity<?> getAllStartups(@RequestParam(required = false) Boolean published){
        return new ResponseEntity(startupService.getStartupsByPublishedStatus(published), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createStartup(@Valid @RequestBody StartupOperation startupOperation) {
        Startup startup = startupService.createStartup(startupOperation);
        return new ResponseEntity(startupRepository.save(startup), HttpStatus.CREATED);
    }


}
