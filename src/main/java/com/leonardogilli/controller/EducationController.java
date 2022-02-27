package com.leonardogilli.controller;

import com.leonardogilli.dto.EducationDto;
import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.entity.Education;
import com.leonardogilli.security.entity.User;
import com.leonardogilli.security.service.UserService;
import com.leonardogilli.service.EducationService;
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
@RequestMapping("/api/education")
@CrossOrigin(origins = "http://localhost:4200")
public class EducationController {
    
    @Autowired
    EducationService educationService;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Education>> list() {
        List<Education> list = educationService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @GetMapping("/details/{id}")
    public ResponseEntity<Education> getById(@PathVariable("id") int id) {
        if(!educationService.existsById(id))
            return new ResponseEntity(new Mensaje("Education not found"), HttpStatus.NOT_FOUND);
        Education education = educationService.get(id).get();
        return new ResponseEntity(education, HttpStatus.OK);
    }

    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody EducationDto educationDto, Principal principal) {
        if(StringUtils.isBlank(educationDto.getSchool()))
            return new ResponseEntity(new Mensaje("school can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(educationDto.getTitle()))
            return new ResponseEntity(new Mensaje("title can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(educationDto.getLocation()))
            return new ResponseEntity(new Mensaje("location can not be empty"), HttpStatus.BAD_REQUEST);
                
        Education education = new Education(
                educationDto.getSchool(),
                educationDto.getTitle(), 
                educationDto.getImg(), 
                educationDto.getCareer(), 
                educationDto.getStartTime(), 
                educationDto.getEndTime(), 
                educationDto.getLocation()
        );
        
        User user = userService.getByUsername(principal.getName()).get();
        education.setUser(user);

        educationService.save(education);
        return new ResponseEntity(new Mensaje("Education created"), HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody EducationDto educationDto, Principal principal) {
        if (!educationService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!educationService.existsById(id))
            return new ResponseEntity(new Mensaje("Education not found"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(educationDto.getSchool()))
            return new ResponseEntity(new Mensaje("school can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(educationDto.getTitle()))
            return new ResponseEntity(new Mensaje("title can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(educationDto.getLocation()))
            return new ResponseEntity(new Mensaje("location can not be empty"), HttpStatus.BAD_REQUEST);
        
        Education education = educationService.get(id).get();
        education.setSchool(educationDto.getSchool());
        education.setTitle(educationDto.getTitle());
        education.setImg(educationDto.getImg());
        education.setCareer(educationDto.getCareer());
        education.setStartTime(educationDto.getStartTime());
        education.setEndTime(educationDto.getEndTime());
        education.setLocation(educationDto.getLocation());
        
        educationService.save(education);
        return new ResponseEntity(new Mensaje("Education updated"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, Principal principal) {
        if (!educationService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!educationService.existsById(id))
            return new ResponseEntity(new Mensaje("Education not found"), HttpStatus.NOT_FOUND);
        educationService.delete(id);
        return new ResponseEntity(new Mensaje("Education deleted"), HttpStatus.OK);
    }
}
