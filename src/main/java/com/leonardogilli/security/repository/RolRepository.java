package com.leonardogilli.security.repository;

import com.leonardogilli.security.entity.Rol;
import com.leonardogilli.security.enums.RolName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(RolName rolName);
}
