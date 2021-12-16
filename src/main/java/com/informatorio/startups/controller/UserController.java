package com.informatorio.startups.controller;

import com.informatorio.startups.dto.UserOperation;
import com.informatorio.startups.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String countryName,
            @RequestParam(required = false) String stateName,
            @RequestParam(required = false) String cityName){
        return new ResponseEntity(userService.getUsers(date, countryName,stateName,cityName), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return new ResponseEntity(userService.getById(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/votes")
    public ResponseEntity<?> getVotes(@PathVariable Long userId){
        return new ResponseEntity(userService.getVotes(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserOperation userOperation){
        return new ResponseEntity(userService.createUser(userOperation), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long userId,
            @RequestBody UserOperation userOperation){
        return new ResponseEntity(userService.updateUser(userId, userOperation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        return new ResponseEntity(userService.deleteUser(userId), HttpStatus.OK);
    }

}
