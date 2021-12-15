package com.informatorio.startups.controller;

import com.informatorio.startups.dto.VoteOperation;
import com.informatorio.startups.dto.VotePlatform;
import com.informatorio.startups.entity.Startup;
import com.informatorio.startups.entity.User;
import com.informatorio.startups.entity.Vote;
import com.informatorio.startups.exception.DuplicateEntryException;
import com.informatorio.startups.repository.StartupRepository;
import com.informatorio.startups.repository.UserRepository;
import com.informatorio.startups.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/vote")
public class VoteController {
    private final UserRepository userRepository;
    private final StartupRepository startupRepository;
    private final VoteRepository voteRepository;

    public VoteController(UserRepository userRepository, StartupRepository startupRepository,
                          VoteRepository voteRepository){
        this.userRepository = userRepository;
        this.startupRepository = startupRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<?> getVotesFromUser(@PathVariable Long userId){
        return new ResponseEntity(voteRepository.findByUser_Id(userId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createVote(@Valid @RequestBody VoteOperation voteOperation){
        User user = userRepository.findById(voteOperation.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        Startup startup = startupRepository.findById(voteOperation.getStartupId())
                .orElseThrow(() -> new EntityNotFoundException("Startup not found."));
        List<Vote> userVotes = voteRepository.findByUser_Id(voteOperation.getUserId());

        for (Vote v : userVotes) {
            if (v.getStartup().getId() == startup.getId()){
                throw new DuplicateEntryException("This user has already voted for this startup.");
            }
        }
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setStartup(startup);
        vote.setPlatform(voteOperation.getPlatform());
        return new ResponseEntity(voteRepository.save(vote), HttpStatus.CREATED);
    }
}
