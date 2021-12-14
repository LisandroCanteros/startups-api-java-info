package com.informatorio.startups.repository;

import com.informatorio.startups.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    public Optional<Country> findByName(String name);
}
