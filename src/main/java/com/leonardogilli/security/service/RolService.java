package com.leonardogilli.security.service;

import com.leonardogilli.security.entity.Rol;
import com.leonardogilli.security.enums.RolName;
import com.leonardogilli.security.repository.RolRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolService {
    
    @Autowired
    RolRepository rolRepository;
    
    public Optional<Rol> getByRolName(RolName rolName) {
        return rolRepository.findByRolName(rolName);
    }
}
