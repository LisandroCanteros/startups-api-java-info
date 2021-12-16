package com.informatorio.startups.service;

import com.informatorio.startups.dto.UserOperation;
import com.informatorio.startups.entity.*;
import com.informatorio.startups.exception.UserCreationException;
import com.informatorio.startups.repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final VoteRepository voteRepository;

    public UserService(UserRepository userRepository,
                       CountryRepository countryRepository,
                       StateRepository stateRepository,
                       CityRepository cityRepository,
                       VoteRepository voteRepository){
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.voteRepository = voteRepository;
    }

    public List<User> getUsers(LocalDate date, String countryName, String stateName, String cityName){
        if (date != null){
            return userRepository.findByCreationDateAfter(date.atStartOfDay());
        }
        if (countryName != null || stateName != null || cityName != null) {
            return getUsersFrom(countryName, stateName, cityName);
        }
        return userRepository.findAll();
    }

    public User getById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public List<Vote> getVotes(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        List<Vote> votes = voteRepository.findByUser_Id(userId);
        return votes;
    }

    public boolean validateLocation(Country country, State state, City city){
        List<State> states = country.getStates();
        List<City> cities = state.getCities();
        if (states.contains(state) && cities.contains(city)){
            return true;
        }
        throw new EntityNotFoundException("Invalid location.");
    }

    public List<User> getUsersFrom(String countryName, String stateName, String cityName){
        if (countryName == null){
            throw new EntityNotFoundException("Country not found.");
        }
        if (stateName != null && cityName != null){
            Country country = countryRepository.findByName(countryName)
                    .orElseThrow(()-> new EntityNotFoundException("Country not found."));
            State state = stateRepository.findByName(stateName)
                    .orElseThrow(() -> new EntityNotFoundException("State not found."));
            City city = cityRepository.findByName(cityName)
                    .orElseThrow(() -> new EntityNotFoundException("City not found."));
            if (validateLocation(country, state, city)) {
                return userRepository.findByCountry_IdAndState_IdAndCity_Id(
                        country.getId(), state.getId(), city.getId());
            }
        }else if (stateName != null){
            Country country = countryRepository.findByName(countryName)
                    .orElseThrow(()-> new EntityNotFoundException("Country not found."));
            State state = stateRepository.findByName(stateName)
                    .orElseThrow(() -> new EntityNotFoundException("State not found."));
            return userRepository.findByCountry_IdAndState_Id(country.getId(),state.getId());
        }else if (cityName == null){
            Country country = countryRepository.findByName(countryName)
                    .orElseThrow(()-> new EntityNotFoundException("Country not found."));
            return userRepository.findByCountry_Id(country.getId());
        }
        throw new EntityNotFoundException("Invalid location.");
    }

    public User createUser(UserOperation userOperation){
        Country country = countryRepository.findByName(userOperation.getCountryName())
                .orElseThrow(() -> new EntityNotFoundException("Country not found."));
        State state = stateRepository.findByName(userOperation.getStateName())
                .orElseThrow(() -> new EntityNotFoundException("State not found."));
        City city = cityRepository.findByName(userOperation.getCityName())
                .orElseThrow(() -> new EntityNotFoundException("City not found"));

        if (validateLocation(country, state, city)){
            User user = new User();
            user.setFirstName(userOperation.getFirstName());
            user.setLastName(userOperation.getLastName());
            user.setEmail(userOperation.getEmail());
            user.setPassword(userOperation.getPassword());
            user.setUserType(userOperation.getUserType());
            user.setCountry(country);
            user.setState(state);
            user.setCity(city);
            userRepository.save(user);
            return user;
        }
        throw new UserCreationException("Invalid data.");
    }

    public User updateUser(Long userId, UserOperation userOperation){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        if (userOperation.getFirstName() != null && !userOperation.getFirstName().isBlank()){
            user.setFirstName(userOperation.getFirstName());
        }
        if (userOperation.getLastName() != null && !userOperation.getLastName().isBlank()){
            user.setLastName(userOperation.getLastName());
        }
        if (userOperation.getPassword() != null && !userOperation.getPassword().isBlank()){
            user.setPassword(userOperation.getPassword());
        }
        if (userOperation.getUserType() != null){
            user.setUserType(userOperation.getUserType());
        }
        City city = cityRepository.findByName(userOperation.getCityName())
                .orElse(user.getCity());
        State state = stateRepository.findByName(userOperation.getStateName())
                .orElse(user.getState());
        Country country = countryRepository.findByName(userOperation.getCountryName())
                .orElse(user.getCountry());
        user.setCity(city);
        user.setState(state);
        user.setCountry(country);
        validateLocation(user.getCountry(), user.getState(), user.getCity());
        userRepository.save(user);
        return user;
    }

    public String deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.delete(user);
        return "User " + user.getId() + " deleted.";
    }
}
