package com.informatorio.startups.service;

import com.informatorio.startups.dto.UserOperation;
import com.informatorio.startups.entity.City;
import com.informatorio.startups.entity.Country;
import com.informatorio.startups.entity.State;
import com.informatorio.startups.entity.User;
import com.informatorio.startups.exception.UserCreationException;
import com.informatorio.startups.repository.CityRepository;
import com.informatorio.startups.repository.CountryRepository;
import com.informatorio.startups.repository.StateRepository;
import com.informatorio.startups.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public UserService(UserRepository userRepository,
                       CountryRepository countryRepository,
                       StateRepository stateRepository,
                       CityRepository cityRepository){
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    public boolean validateLocation(Country country, State state, City city){
        List<State> states = country.getStates();
        List<City> cities = state.getCities();
        if (states.contains(state) && cities.contains(city)){
            return true;
        }
        throw new EntityNotFoundException("Invalid location.");
    }

    public User createUser(UserOperation userOperation){
        Country country = countryRepository.findByName(userOperation.getCountryName())
                .orElseThrow(() -> new EntityNotFoundException("Country not found."));
        State state = stateRepository.findStateByName(userOperation.getStateName())
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
            return user;
        }
        throw new UserCreationException("Invalid data.");
    }

    public List<User> getUsersFrom(String countryName, String stateName, String cityName){
        if (countryName == null){
            throw new EntityNotFoundException("Country not found.");
        }
        if (stateName != null && cityName != null){
            Country country = countryRepository.findByName(countryName)
                    .orElseThrow(()-> new EntityNotFoundException("Country not found."));
            State state = stateRepository.findStateByName(stateName)
                    .orElseThrow(() -> new EntityNotFoundException("State not found."));
            City city = cityRepository.findByName(cityName)
                    .orElseThrow(() -> new EntityNotFoundException("City not found."));
            if (validateLocation(country, state, city)) {
                List<User> users = userRepository.findByCountry_IdAndState_IdAndCity_Id(
                        country.getId(), state.getId(), city.getId());
                return users;
            }
        }else if (stateName != null && cityName == null){
            Country country = countryRepository.findByName(countryName)
                    .orElseThrow(()-> new EntityNotFoundException("Country not found."));
            State state = stateRepository.findStateByName(stateName)
                    .orElseThrow(() -> new EntityNotFoundException("State not found."));
            List<User> users = userRepository.findByCountry_IdAndState_Id(country.getId(),state.getId());
            return users;
        }else if (stateName == null  && cityName == null){
            Country country = countryRepository.findByName(countryName)
                    .orElseThrow(()-> new EntityNotFoundException("Country not found."));
            List<User> users = userRepository.findByCountry_Id(country.getId());
            return users;
        }
        throw new EntityNotFoundException("Invalid location.");
    }
}
