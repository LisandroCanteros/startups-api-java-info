package com.informatorio.startups.service;

import com.informatorio.startups.dto.AddStartupToEventOperation;
import com.informatorio.startups.dto.StartupOperation;
import com.informatorio.startups.entity.Event;
import com.informatorio.startups.entity.Startup;
import com.informatorio.startups.entity.Tag;
import com.informatorio.startups.entity.User;
import com.informatorio.startups.exception.DuplicateEntryException;
import com.informatorio.startups.repository.EventRepository;
import com.informatorio.startups.repository.StartupRepository;
import com.informatorio.startups.repository.TagRepository;
import com.informatorio.startups.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StartupService {
    private final StartupRepository startupRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final EventRepository eventRepository;

    public StartupService(StartupRepository startupRepository, UserRepository userRepository,
                          TagRepository tagRepository, EventRepository eventRepository) {
        this.startupRepository = startupRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.eventRepository = eventRepository;
    }

    public List<Startup> getStartupsBy(Boolean published, String tag){
        if (published != null){
            return startupRepository.findByPublished(published);
        }
        if (tag != null && !tag.isBlank()){
            List<Tag> tags = tagRepository.findByNameIgnoreCaseContaining(tag);
            List<Startup> startups = new ArrayList<>();
            for (Tag t: tags) {
                startups = startupRepository.findByTags_Id(t.getId());
            }
            return startups;
        }
        return startupRepository.findAll();
    }

    public boolean isStartupNameValid(User owner, String newStartupName){
        for (Startup s: owner.getAllStartups()) {
            if (s.getName().equals(newStartupName)){
                return false;
            }
        }
        return true;
    }

    public Startup createStartup(StartupOperation startupOperation){
        User owner = userRepository.findById(startupOperation.getIdOwner())
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        if (isStartupNameValid(owner, startupOperation.getName())){
            Startup startup = new Startup();
            startup.setOwner(owner);
            startup.setName(startupOperation.getName());
            startup.setDescription(startupOperation.getDescription());
            startup.setBody(startupOperation.getBody());
            startup.setGoal(startupOperation.getGoal());
            startup.setPublished(startupOperation.getPublished());
            startupOperation.getTags().stream()
                    .filter(tag -> tag.getName() != null && !tag.getName().isBlank())
                    .forEach(startup::addTag);
            startupOperation.getImagesUrl().stream()
                    .filter(img -> img.getUrl() != null && !img.getUrl().isBlank())
                    .forEach(startup::addImageUrl);
            return startup;
        }
        throw new DuplicateEntryException("This user already has a startup called "
                +startupOperation.getName());
    }

    public String addStartupToEvent(Long startupId, AddStartupToEventOperation addStartupToEventOperation){
        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new EntityNotFoundException("Startup not found."));
        Event event = eventRepository.findByName(addStartupToEventOperation.getName())
                .orElseThrow(() -> new EntityNotFoundException("Event not found."));
        if (!startup.getPublished()){
            throw new EntityNotFoundException("Startup not published");
        }
        if (event.getStartups().contains(startup)){
            throw new DuplicateEntryException("Startup already in event.");
        }
        startup.addEvent(event);
        startupRepository.save(startup);
        return "Startup ID: " + startup.getId() + " added to event ID: " + event.getId();
    }

    public String deleteStartup(Long startupId){
        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new EntityNotFoundException("Startup not found."));
        startupRepository.delete(startup);
        return "Startup ID: " + startup.getId() + " deleted.";
    }

    public Startup updateStartup(Long startupId, StartupOperation startupOperation){
        Startup startup = startupRepository.findById(startupId)
                .orElseThrow(() -> new EntityNotFoundException("Startup not found."));
        User owner = userRepository.getById(startup.getOwner().getId());
        if (startupOperation.getName() != null && !startupOperation.getName().isBlank() &&
                isStartupNameValid(owner, startupOperation.getName())){
            startup.setName(startupOperation.getName());
        }
        if (startupOperation.getBody() != null && !startupOperation.getBody().isBlank()){
            startup.setBody(startupOperation.getBody());
        }
        if (startupOperation.getDescription() != null && !startupOperation.getDescription().isBlank()){
            startup.setDescription(startupOperation.getDescription());
        }
        if (startupOperation.getTags() != null && startupOperation.getTags().isEmpty()){
            startup.getTags().clear();
        }else if (startupOperation.getTags() != null){
            startupOperation.getTags().stream()
                    .forEach(tag -> startup.getTags().add(tag));
        }
        if (startupOperation.getImagesUrl() != null && startupOperation.getImagesUrl().isEmpty()){
            startup.getImageUrls().clear();
        }else if (startupOperation.getImagesUrl() != null){
            startupOperation.getImagesUrl().stream()
                    .forEach(image -> startup.getImageUrls().add(image));
        }
        if (startupOperation.getGoal() != null &&
                startupOperation.getGoal().compareTo(BigDecimal.valueOf(0)) > -1){
            startup.setGoal(startupOperation.getGoal());
        }
        if (startupOperation.getPublished() != null){
            startup.setPublished(startupOperation.getPublished());
        }
       return startupRepository.save(startup);
    }
}
