package com.informatorio.startups.service;

import com.informatorio.startups.dto.EventOperation;
import com.informatorio.startups.entity.EventStatus;
import com.informatorio.startups.dto.StartupDto;
import com.informatorio.startups.entity.Event;
import com.informatorio.startups.entity.Startup;
import com.informatorio.startups.entity.Vote;
import com.informatorio.startups.exception.DuplicateEntryException;
import com.informatorio.startups.repository.EventRepository;
import com.informatorio.startups.repository.VoteRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final VoteRepository voteRepository;
    public EventService(EventRepository eventRepository, VoteRepository voteRepository){
        this.eventRepository = eventRepository;
        this.voteRepository = voteRepository;
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Boolean eventNameExists(String eventName){
        return eventRepository.findByName(eventName).isPresent();
    }

    public EventOperation calculateVotes(Long eventId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found."));
        List<Vote> votes = voteRepository.findByEvent_Id(eventId);
        Integer amount = 0;
        EventOperation eventOperation = new EventOperation();
        eventOperation.setName(event.getName());
        eventOperation.setDescription(event.getDescription());
        eventOperation.setStatus(event.getStatus());
        eventOperation.setPrizepool(event.getPrizepool());
        List<StartupDto> startupDtos = new ArrayList<>();
        for (Startup s : event.getStartups()) {
            StartupDto startupDto = new StartupDto();
            startupDto.setId(s.getId());
            startupDto.setName(s.getName());
            startupDto.setDescription(s.getDescription());
            startupDto.setOwnerId(s.getOwner().getId());
            startupDto.setOwnerName(s.getOwner().getFirstName());
            for (Vote vote:s.getVotes()) {
                if (vote.getEvent() == event){
                    amount += 1;
                }
            }
            startupDto.setAmountVotes(amount);
            amount = 0;
            startupDtos.add(startupDto);
        }
        eventOperation.setStartups(startupDtos);
        return eventOperation;
    }

    public EventStatus determineStatus(LocalDateTime endDate){
        LocalDateTime currentDate = LocalDateTime.now();
        Duration duration = Duration.between(currentDate, endDate);
        if (duration.toMinutes() < 0){
            return EventStatus.ENDED;
        }
        return EventStatus.OPEN;
    }

    public Event createEvent(EventOperation eventOperation){
        if (eventNameExists(eventOperation.getName())){
            throw new DuplicateEntryException("Event name already exists.");
        }
        Event event = new Event();
        event.setName(eventOperation.getName());
        event.setDescription(eventOperation.getDescription());
        event.setEndDate(eventOperation.getEndDate());
        event.setPrizepool(eventOperation.getPrizepool());
        event.setStatus(determineStatus(eventOperation.getEndDate()));
        return eventRepository.save(event);
    }
}
