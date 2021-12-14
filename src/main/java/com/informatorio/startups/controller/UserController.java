package com.informatorio.startups.controller;

import com.informatorio.startups.dto.UserOperation;
import com.informatorio.startups.entity.User;
import com.informatorio.startups.repository.UserRepository;
import com.informatorio.startups.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String countryName,
            @RequestParam(required = false) String stateName,
            @RequestParam(required = false) String cityName){
        if (date != null){
            List<User> users = userRepository.findByCreationDateAfter(date.atStartOfDay());
            return new ResponseEntity(users, HttpStatus.OK);
        }
        if (countryName != null || stateName != null || cityName != null) {
            List<User> users = userService.getUsersFrom(countryName, stateName, cityName);
            return new ResponseEntity(users, HttpStatus.OK);
        }
        return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new ResponseEntity(userRepository.findById(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserOperation userOperation){
        User user = userService.createUser(userOperation);
        return new ResponseEntity(userRepository.save(user), HttpStatus.CREATED);
    }
}
