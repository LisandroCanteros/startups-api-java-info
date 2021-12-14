package com.informatorio.startups.repository;

import com.informatorio.startups.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByCreationDateAfter(LocalDateTime date);
    public List<User> findByCountry_Id(Long countryId);
    public List<User> findByCountry_IdAndState_Id(Long countryId, Long stateId);
    public List<User> findByCountry_IdAndState_IdAndCity_Id(Long countryId, Long stateId, Long cityId);


}
