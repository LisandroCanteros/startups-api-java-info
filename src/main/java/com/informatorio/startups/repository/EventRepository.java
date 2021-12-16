package com.informatorio.startups.repository;

import com.informatorio.startups.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public Optional<Event> findByName(String name);
}
