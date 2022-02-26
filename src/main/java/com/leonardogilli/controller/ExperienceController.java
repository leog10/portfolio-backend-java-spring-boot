package com.leonardogilli.controller;

import com.leonardogilli.dto.ExperienceDto;
import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.entity.Experience;
import com.leonardogilli.entity.Persona;
import com.leonardogilli.service.ExperienceService;
import com.leonardogilli.service.PersonaService;
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
@RequestMapping("/api/experience")
@CrossOrigin(origins = "http://localhost:4200")
public class ExperienceController {
    
    @Autowired
    ExperienceService experienceService;
    
    @Autowired
    PersonaService personaService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Experience>> list() {
        List<Experience> list = experienceService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

     @GetMapping("/details/{id}")
    public ResponseEntity<Experience> getById(@PathVariable("id") int id) {
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("Experience not found"), HttpStatus.NOT_FOUND);
        Experience experience = experienceService.get(id).get();
        return new ResponseEntity(experience, HttpStatus.OK);
    }
    
    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody ExperienceDto experienceDto) {
        if(StringUtils.isBlank(experienceDto.getPosition()))
            return new ResponseEntity(new Mensaje("position can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(experienceDto.getCompany()))
            return new ResponseEntity(new Mensaje("company can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(experienceDto.getMode()))
            return new ResponseEntity(new Mensaje("mode can not be empty"), HttpStatus.BAD_REQUEST);
        
        Experience experience = new Experience(
                experienceDto.getPosition(), 
                experienceDto.getCompany(), 
                experienceDto.getImg(), 
                experienceDto.getMode(), 
                experienceDto.getStartTime(), 
                experienceDto.getEndTime(), 
                experienceDto.getTimeAtPosition(), 
                experienceDto.getLocation());
        
        // MODIFICAR CON Principal principal get()!!!!!!!!!/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
        Persona persona = personaService.get(1).get();
        experience.setPersona(persona);
        
        experienceService.save(experience);
        return new ResponseEntity(new Mensaje("Experience created"), HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ExperienceDto experienceDto) {
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("Not found"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(experienceDto.getPosition()))
            return new ResponseEntity(new Mensaje("position can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(experienceDto.getCompany()))
            return new ResponseEntity(new Mensaje("company can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(experienceDto.getMode()))
            return new ResponseEntity(new Mensaje("mode can not be empty"), HttpStatus.BAD_REQUEST);
        
        Experience experience = experienceService.get(id).get();
        experience.setPosition(experienceDto.getPosition());
        experience.setCompany(experienceDto.getCompany());
        experience.setImg(experienceDto.getImg());
        experience.setMode(experienceDto.getMode());
        experience.setStartTime(experienceDto.getStartTime());
        experience.setEndTime(experienceDto.getEndTime());
        experience.setTimeAtPosition(experienceDto.getTimeAtPosition());
        experience.setLocation(experienceDto.getLocation());
        
        experienceService.save(experience);
        return new ResponseEntity(new Mensaje("Experience updated"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("Experience not found"), HttpStatus.NOT_FOUND);
        experienceService.delete(id);
        return new ResponseEntity(new Mensaje("Experience deleted"), HttpStatus.OK);
    }
}
