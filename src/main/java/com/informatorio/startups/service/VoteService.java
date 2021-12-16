package com.informatorio.startups.service;

import com.informatorio.startups.dto.VoteOperation;
import com.informatorio.startups.entity.Event;
import com.informatorio.startups.entity.Startup;
import com.informatorio.startups.entity.User;
import com.informatorio.startups.entity.Vote;
import com.informatorio.startups.exception.DuplicateEntryException;
import com.informatorio.startups.repository.EventRepository;
import com.informatorio.startups.repository.StartupRepository;
import com.informatorio.startups.repository.UserRepository;
import com.informatorio.startups.repository.VoteRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
public class VoteService {
    private final UserRepository userRepository;
    private final StartupRepository startupRepository;
    private final VoteRepository voteRepository;
    private final EventRepository eventRepository;

    public VoteService(UserRepository userRepository, StartupRepository startupRepository,
                          VoteRepository voteRepository, EventRepository eventRepository){
        this.userRepository = userRepository;
        this.startupRepository = startupRepository;
        this.voteRepository = voteRepository;
        this.eventRepository= eventRepository;
    }

    public List<Vote> getVotesByUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        List<Vote> votes = voteRepository.findByUser_Id(userId);
        return votes;
    }

    public Vote createVote(VoteOperation voteOperation){
        User user = userRepository.findById(voteOperation.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        Startup startup = startupRepository.findById(voteOperation.getStartupId())
                .orElseThrow(() -> new EntityNotFoundException("Startup not found."));

        List<Vote> userVotes = voteRepository.findByUser_Id(voteOperation.getUserId());
        for (Vote v : userVotes) {
            if (Objects.equals(v.getStartup().getId(), startup.getId()) && voteOperation.getEventId() != null
            && Objects.equals(voteOperation.getEventId(), startup.getId())){
                throw new DuplicateEntryException("This user has already voted for this startup.");
            }
        }
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setStartup(startup);
        vote.setPlatform(voteOperation.getPlatform());
        if (voteOperation.getEventId() != null) {
            Event event = eventRepository.findById(voteOperation.getEventId())
                    .orElseThrow(() -> new EntityNotFoundException("Event not found."));
            vote.setEvent(event);
        }
        voteRepository.save(vote);
        return vote;
    }
}
