package com.informatorio.startups.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.informatorio.startups.entity.UserType;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class UserOperation {
    @NotBlank
    @JsonProperty(value = "first_name")
    private String firstName;
    @NotBlank
    @JsonProperty(value = "last_name")
    private String lastName;
    @NotBlank
    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Email structure is not valid." )
    @Column(unique = true)
    private String email;
    @NotBlank
    @Size(min = 8, max = 20, message = "Password must be between 8-20 characters long.")
    private String password;
    @NotNull
    @JsonProperty(value = "user_type")
    private UserType userType;
    @NotBlank
    @JsonProperty(value = "country_name")
    private String countryName;
    @NotBlank
    @JsonProperty(value = "state_name")
    private String stateName;
    @NotBlank
    @JsonProperty(value = "city_name")
    private String cityName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
