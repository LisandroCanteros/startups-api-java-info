package com.informatorio.startups.dto;

public class StartupDto {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private String ownerName;
    private Integer amountVotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getAmountVotes() {
        return amountVotes;
    }

    public void setAmountVotes(Integer amountVotes) {
        this.amountVotes = amountVotes;
    }
}
