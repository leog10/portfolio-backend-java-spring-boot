package com.leonardogilli.service;

import com.leonardogilli.entity.Project;
import com.leonardogilli.repository.ProjectRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {
    
    @Autowired
    ProjectRepository projectRepository;
    
    public List<Project> list() {
        return projectRepository.findAll();
    }
    
    public Optional<Project> get(int id) {
        return projectRepository.findById(id);
    }
    
    public void delete(int id) {
        projectRepository.deleteById(id);
    }
    
    public void save(Project project) {
        projectRepository.save(project);
    }
    
    public boolean existsById(int id) {
        return projectRepository.existsById(id);
    }
    
    public List<Project> listByUserId(int id) {
        return projectRepository.findByUserId(id);
    }
}
