package com.informatorio.startups.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.informatorio.startups.dto.VotePlatform;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private VotePlatform platform;
    @CreationTimestamp
    private LocalDateTime creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Startup startup;

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", platform=" + platform +
                ", creationDate=" + creationDate +
                ", user_id=" + user.getId() +
                ", first_name" + user.getFirstName()+
                ", startup_id=" + startup.getId() +
                ", startup_name=" + startup.getName() +
                '}';
    }

    public Long getId() {
        return id;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId(){
        return user.getId();
    }

    @JsonIgnore
    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }

    public Long getStartupId(){
        return startup.getId();
    }

    public VotePlatform getPlatform() {
        return platform;
    }

    public void setPlatform(VotePlatform platform) {
        this.platform = platform;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

}
