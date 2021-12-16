package com.informatorio.startups.dto;

import javax.validation.constraints.NotBlank;

public class AddStartupToEventOperation {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
