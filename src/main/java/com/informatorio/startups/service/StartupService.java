package com.informatorio.startups.service;

import com.informatorio.startups.dto.StartupOperation;
import com.informatorio.startups.entity.Startup;
import com.informatorio.startups.entity.User;
import com.informatorio.startups.exception.DuplicateEntryException;
import com.informatorio.startups.repository.StartupRepository;
import com.informatorio.startups.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StartupService {
    private final StartupRepository startupRepository;
    private final UserRepository userRepository;

    public StartupService(StartupRepository startupRepository, UserRepository userRepository) {
        this.startupRepository = startupRepository;
        this.userRepository = userRepository;
    }

    public List<Startup> getStartupsByPublishedStatus (Boolean published){
        if (published != null){
            return startupRepository.findByPublished(published);
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
            return startup;
        }
        throw new DuplicateEntryException("This user already has a startup called "
                +startupOperation.getName());
    }
}
