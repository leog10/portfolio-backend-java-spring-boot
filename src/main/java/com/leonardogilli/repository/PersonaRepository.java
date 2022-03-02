package com.leonardogilli.repository;

import com.leonardogilli.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    boolean existsByUserId(int userId);
}
