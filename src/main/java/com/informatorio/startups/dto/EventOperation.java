package com.informatorio.startups.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.informatorio.startups.entity.EventStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventOperation {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private EventStatus status;
    @NotNull
    @JsonProperty(value = "end_date")
    private LocalDateTime endDate;
    @NotNull
    @PositiveOrZero
    private BigDecimal prizepool;
    private List<StartupDto> startups = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrizepool() {
        return prizepool;
    }

    public void setPrizepool(BigDecimal prizepool) {
        this.prizepool = prizepool;
    }

    public List<StartupDto> getStartups() {
        return startups;
    }

    public void setStartups(List<StartupDto> startups) {
        this.startups = startups;
    }
}
