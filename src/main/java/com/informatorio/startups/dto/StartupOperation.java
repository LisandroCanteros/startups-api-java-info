package com.informatorio.startups.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class StartupOperation {
    @NotBlank(message = "Name cannot be null or empty.")
    private String name;
    @NotBlank(message = "Description cannot be null or empty.")
    private String description;
    @Lob
    @NotBlank(message = "Body cannot be null or empty.")
    private String body;
    private BigDecimal goal;
    private Boolean published;
    @NotNull
    @Positive
    @JsonProperty(value = "id_owner")
    private Long idOwner;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

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

    public Long getIdOwner() {
        return idOwner;
    }
}
