package com.informatorio.startups.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class ImageUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Image url cannot be empty nor null.")
    private String url;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private Startup startup;

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }
}
