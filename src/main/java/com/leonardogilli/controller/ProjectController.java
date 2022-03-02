package com.leonardogilli.controller;

import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.dto.ProjectDto;
import com.leonardogilli.entity.Project;
import com.leonardogilli.security.entity.User;
import com.leonardogilli.security.service.UserService;
import com.leonardogilli.service.ProjectService;
import java.security.Principal;
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
    UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Project>> list() {
        List<Project> list = projectService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/details/id/{id}")
    public ResponseEntity<Project> getById(@PathVariable("id") int id) {
        if(!projectService.existsById(id))
            return new ResponseEntity(new Mensaje("Project not found"), HttpStatus.NOT_FOUND);
        Project project = projectService.get(id).get();
        return new ResponseEntity(project, HttpStatus.OK);
    }
    
    @GetMapping("/details/{username}")
    public ResponseEntity<List<Project>> getByUsername(@PathVariable("username") String username) {
        if (!userService.existsByUsername(username))
            return new ResponseEntity(new Mensaje("User not found"), HttpStatus.NOT_FOUND);
        User user = userService.getByUsername(username).get();        
        List<Project> list = projectService.listByUserId(user.getId());
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProjectDto projectDto, Principal principal) {
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
        
        User user = userService.getByUsername(principal.getName()).get();
        project.setUser(user);
        
        projectService.save(project);
        return new ResponseEntity(new Mensaje("Project created"), HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProjectDto projectDto, Principal principal) {
        if (!projectService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
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
    public ResponseEntity<?> delete(@PathVariable("id") int id, Principal principal) {
        if (!projectService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!projectService.existsById(id))
            return new ResponseEntity(new Mensaje("Project not found"), HttpStatus.NOT_FOUND);
        projectService.delete(id);
        return new ResponseEntity(new Mensaje("Project deleted"), HttpStatus.OK);
    }
}
