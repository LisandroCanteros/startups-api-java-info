package com.informatorio.startups.repository;

import com.informatorio.startups.entity.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long> {
    public List<Startup> findByPublished(Boolean num);
    public List<Startup> findByTags_Id(Long id);
}
