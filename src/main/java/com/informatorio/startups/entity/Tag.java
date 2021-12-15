package com.informatorio.startups.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "tags")
    private List<Startup> startupList = new ArrayList<>();

    @JsonIgnore
    public Long getId() {
        return id;
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

    @JsonIgnore
    public List<Startup> getStartupList() {
        return startupList;
    }

    public void setStartupList(List<Startup> startupList) {
        this.startupList = startupList;
    }
}
