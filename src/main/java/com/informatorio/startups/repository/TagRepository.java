package com.informatorio.startups.repository;

import com.informatorio.startups.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    public List<Tag> findByNameContaining(String name);
}
