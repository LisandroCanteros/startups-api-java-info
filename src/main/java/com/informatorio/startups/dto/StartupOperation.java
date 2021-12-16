package com.informatorio.startups.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.informatorio.startups.entity.ImageUrl;
import com.informatorio.startups.entity.Tag;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StartupOperation {
    @NotBlank(message = "Name cannot be null or empty.")
    private String name;
    @NotBlank(message = "Description cannot be null or empty.")
    private String description;
    @Lob
    @NotBlank(message = "Body cannot be null or empty.")
    private String body;
    @NotNull
    @PositiveOrZero
    private BigDecimal goal;
    @NotNull
    private Boolean published;
    @NotNull
    @Positive
    @JsonProperty(value = "id_owner")
    private Long idOwner;
    @JsonProperty(value = "images_url")
    private List<ImageUrl> imagesUrl = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    @JsonProperty(value = "events_name")
    private List<String> eventsName = new ArrayList<>();

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

    public List<ImageUrl> getImagesUrl() {
        return imagesUrl;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<String> getEventsName() {
        return eventsName;
    }
}
