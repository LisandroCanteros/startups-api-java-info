package com.informatorio.startups.repository;

import com.informatorio.startups.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    public List<Vote> findByUser_Id(Long userId);
    public List<Vote> findByEvent_Id(Long eventId);
}
