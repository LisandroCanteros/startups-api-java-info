package com.informatorio.startups.service;

import com.informatorio.startups.dto.StartupOperation;
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
}
