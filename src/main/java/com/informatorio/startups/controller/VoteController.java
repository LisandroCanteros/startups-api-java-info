package com.informatorio.startups.controller;

import com.informatorio.startups.dto.VoteOperation;
import com.informatorio.startups.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/vote")
public class VoteController {
    private final VoteService voteService;
    public VoteController(VoteService voteService){
        this.voteService = voteService;
    }

    @PostMapping()
    public ResponseEntity<?> createVote(@Valid @RequestBody VoteOperation voteOperation){
        return new ResponseEntity(voteService.createVote(voteOperation), HttpStatus.CREATED);
    }
}
