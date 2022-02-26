package com.leonardogilli.controller;

import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.dto.ProjectDto;
import com.leonardogilli.entity.Persona;
import com.leonardogilli.entity.Project;
import com.leonardogilli.service.PersonaService;
import com.leonardogilli.service.ProjectService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
    
    @Autowired
    ProjectService projectService;
    
    @Autowired
    PersonaService personaService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Project>> list() {
        List<Project> list = projectService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/details/{id}")
    public ResponseEntity<Project> getById(@PathVariable("id") int id) {
        if(!projectService.existsById(id))
            return new ResponseEntity(new Mensaje("Project not found"), HttpStatus.NOT_FOUND);
        Project project = projectService.get(id).get();
        return new ResponseEntity(project, HttpStatus.OK);
    }
    
    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProjectDto projectDto) {
        if(StringUtils.isBlank(projectDto.getName()))
            return new ResponseEntity(new Mensaje("name can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(projectDto.getDescription()))
            return new ResponseEntity(new Mensaje("description can not be empty"), HttpStatus.BAD_REQUEST);
        
        Project project = new Project(
                projectDto.getName(), 
                projectDto.getImg(), 
                projectDto.getDescription(), 
                projectDto.getStartTime(), 
                projectDto.getEndTime());
        
        // MODIFICAR CON Principal principal get()!!!!!!!!!/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
        Persona persona = personaService.get(1).get();
        project.setPersona(persona);
        
        projectService.save(project);
        return new ResponseEntity(new Mensaje("Project created"), HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProjectDto projectDto) {
        if (!projectService.existsById(id))
            return new ResponseEntity(new Mensaje("Project not found"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(projectDto.getName()))
            return new ResponseEntity(new Mensaje("name can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(projectDto.getDescription()))
            return new ResponseEntity(new Mensaje("description can not be empty"), HttpStatus.BAD_REQUEST);
        
        Project project = projectService.get(id).get();
        project.setName(projectDto.getName());
        project.setImg(projectDto.getImg());
        project.setDescription(projectDto.getDescription());
        project.setStartTime(projectDto.getStartTime());
        project.setEndTime(projectDto.getEndTime());
        
        projectService.save(project);
        return new ResponseEntity(new Mensaje("Project updated"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!projectService.existsById(id))
            return new ResponseEntity(new Mensaje("Project not found"), HttpStatus.NOT_FOUND);
        projectService.delete(id);
        return new ResponseEntity(new Mensaje("Project deleted"), HttpStatus.OK);
    }
}
