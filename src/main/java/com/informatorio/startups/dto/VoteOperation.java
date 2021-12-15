package com.informatorio.startups.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class VoteOperation {
    @NotNull
    private VotePlatform platform;
    @NotNull
    @JsonProperty(value = "user_id")
    private Long userId;
    @NotNull
    @JsonProperty(value = "startup_id")
    private Long startupId;

    public VotePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(VotePlatform platform) {
        this.platform = platform;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStartupId() {
        return startupId;
    }

    public void setStartupId(Long startupId) {
        this.startupId = startupId;
    }
}
