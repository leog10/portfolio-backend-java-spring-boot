package com.leonardogilli.service;

import com.leonardogilli.entity.Skill;
import com.leonardogilli.repository.SkillRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SkillService {
    
    @Autowired
    SkillRepository skillRepository;
    
    public List<Skill> list() {
        return skillRepository.findAll();
    }
    
    public Optional<Skill> get(int id) {
        return skillRepository.findById(id);
    }
    
    public void delete(int id) {
        skillRepository.deleteById(id);
    }
    
    public void save(Skill skill) {
        skillRepository.save(skill);
    }
    
    public boolean existsById(int id) {
        return skillRepository.existsById(id);
    }
    
    public List<Skill> listByUserId(int id) {
        return skillRepository.findByUserId(id);
    }
}
