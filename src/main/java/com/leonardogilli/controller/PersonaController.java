package com.leonardogilli.controller;

import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.dto.PersonaDto;
import com.leonardogilli.entity.Persona;
import com.leonardogilli.security.entity.User;
import com.leonardogilli.security.service.UserService;
import com.leonardogilli.service.PersonaService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("/api/persona")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonaController {
    
    @Autowired
    PersonaService personaService;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Persona>> list() {
        List<Persona> list = personaService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @RequestMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody PersonaDto personaDto, Principal principal) {        
        if (StringUtils.isBlank(personaDto.getFullName()) || StringUtils.isBlank(personaDto.getAboutMe()))
            return new ResponseEntity(new Mensaje("Fields (name, about) should not be empty"), HttpStatus.BAD_REQUEST);
        
        Persona persona = new Persona(
                personaDto.getFullName(),
                personaDto.getPosition(), 
                personaDto.getLocation(), 
                personaDto.getAboutMe(), 
                personaDto.getAge(), 
                personaDto.getEmail(), 
                personaDto.getBackImg(), 
                personaDto.getProfileImg());
        
        User user = userService.getByUsername(principal.getName()).get();
        persona.setUser(user);
        
        personaService.save(persona);
        return new ResponseEntity(new Mensaje("Persona created"), HttpStatus.CREATED);
    }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id) {
        if (!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        Persona persona = personaService.get(id).get();        
        return new ResponseEntity(persona, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody PersonaDto personaDto, Principal principal) {
        if (!personaService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("Not found"), HttpStatus.NOT_FOUND);
        if (StringUtils.isBlank(personaDto.getFullName()) || StringUtils.isBlank(personaDto.getAboutMe()))
            return new ResponseEntity(new Mensaje("Fields (FullName, AboutMe) can not be empty"), HttpStatus.BAD_REQUEST);
        
        Persona persona = personaService.get(id).get();
        persona.setFullName(personaDto.getFullName());
        persona.setPosition(personaDto.getPosition());
        persona.setLocation(personaDto.getLocation());
        persona.setAboutMe(personaDto.getAboutMe());
        persona.setAge(personaDto.getAge());
        persona.setEmail(personaDto.getEmail());
        persona.setBackImg(personaDto.getBackImg());
        persona.setProfileImg(personaDto.getProfileImg());
        
        personaService.save(persona);
        return new ResponseEntity(new Mensaje("Persona updated"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, Principal principal) {
        if (!personaService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("Persona not found"), HttpStatus.NOT_FOUND);
        personaService.delete(id);
        return new ResponseEntity(new Mensaje("Persona deleted"), HttpStatus.OK);
    }
}
