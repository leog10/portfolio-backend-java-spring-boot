package com.leonardogilli.service;

import com.leonardogilli.entity.Experience;
import com.leonardogilli.repository.ExperienceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExperienceService {
    
    @Autowired
    ExperienceRepository experienceRepository;
    
    public List<Experience> list() {
        return experienceRepository.findAll();
    }
    
    public Optional<Experience> get(int id) {
        return experienceRepository.findById(id);
    }
    
    public void delete(int id) {
        experienceRepository.deleteById(id);
    }
    
    public void save(Experience experience) {
        experienceRepository.save(experience);
    }
    
    public boolean existsById(int id) {
        return experienceRepository.existsById(id);
    }
}
