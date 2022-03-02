package com.leonardogilli.repository;

import com.leonardogilli.entity.Education;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer> {
    List<Education> findByUserId(int Userid);
}
