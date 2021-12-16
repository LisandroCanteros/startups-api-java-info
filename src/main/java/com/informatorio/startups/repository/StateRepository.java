package com.informatorio.startups.repository;

import com.informatorio.startups.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    public Optional<State> findByName(String name);
}
