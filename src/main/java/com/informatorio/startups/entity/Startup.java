package com.informatorio.startups.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Startup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Lob
    private String body;
    @CreationTimestamp
    private LocalDateTime creationDate;
    private BigDecimal goal;
    private Boolean published;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();
    @OneToMany(mappedBy = "startup")
    private List<Vote> votes = new ArrayList<>();
    @OneToMany(mappedBy = "startup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageUrl> imageUrls = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();


    @Override
    public String toString() {
        return "Startup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", creationDate=" + creationDate +
                ", goal=" + goal +
                ", published=" + published +
                ", owner=" + owner +
                ", imageUrls=" + imageUrls +
                ", tags=" + tags +
                '}';
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    @JsonIgnore
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void addImageUrl(ImageUrl imageUrl){
        imageUrls.add(imageUrl);
        imageUrl.setStartup(this);
    }

    public void removeImageUrl(ImageUrl imageUrl){
        imageUrls.remove(imageUrl);
        imageUrl.setStartup(null);
    }

    public List<ImageUrl> getImageUrls() {
        return imageUrls;
    }

    public void addTag(Tag tag){
        tags.add(tag);
        tag.getStartupList().add(this);
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addVote(Vote vote){
        votes.add(vote);
        vote.setStartup(this);
    }

    public void removeVote(Vote vote){
        votes.remove(vote);
        vote.setStartup(null);
    }

    @JsonIgnore
    public List<Vote> getVotes(){
        return votes;
    }

    public void addEvent(Event event){
        events.add(event);
        event.getStartups().add(this);
    }

    public void removeEvent(Event event){
        events.remove(event);
    }

    @JsonIgnore
    public List<Event> getEvents() {
        return events;
    }

    public List<String> getEventNames(){
        List<String> eventNames = new ArrayList<>();
        this.getEvents().stream()
                .forEach(event -> eventNames.add(event.getName()));
        return eventNames;
    }

    public long getAmountVotes(){
        return votes.stream()
                .filter(vote -> vote.getEvent() == null)
                .count();
    }
}
