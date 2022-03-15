package com.leonardogilli.controller;

import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.dto.PersonaDto;
import com.leonardogilli.entity.Persona;
import com.leonardogilli.security.entity.User;
import com.leonardogilli.security.service.UserService;
import com.leonardogilli.service.PersonaService;
import java.security.Principal;
import java.util.ArrayList;
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
        User user = userService.getByUsername(principal.getName()).get();
        
        if (personaService.existsByUserId(user.getId()))
            return new ResponseEntity(new Mensaje("You have already created your profile"), HttpStatus.FORBIDDEN);
        if (StringUtils.isBlank(personaDto.getFirstName()) || StringUtils.isBlank(personaDto.getLastName()))
            return new ResponseEntity(new Mensaje("Fields (firstName, lastName) should not be empty"), HttpStatus.BAD_REQUEST);
        
        Persona persona = new Persona(
                personaDto.getFirstName(),
                personaDto.getLastName(),
                personaDto.getPosition(), 
                personaDto.getLocation(), 
                personaDto.getAboutMe(), 
                personaDto.getBirthday(), 
                personaDto.getBackImg(), 
                personaDto.getProfileImg());
        
        
        persona.setUser(user);
        
        personaService.save(persona);
        return new ResponseEntity(new Mensaje("Persona created"), HttpStatus.CREATED);
    }
    
    @GetMapping("/details/id/{id}")
    public ResponseEntity<Persona> getById(@PathVariable("id") int id) {
        if (!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("Not found"), HttpStatus.NOT_FOUND);
        Persona persona = personaService.get(id).get();        
        return new ResponseEntity(persona, HttpStatus.OK);
    }
    
    @GetMapping("/details/{username}")
    public ResponseEntity<Persona> getByUsername(@PathVariable("username") String username) {
        if (!userService.existsByUsername(username))
            return new ResponseEntity(new Mensaje("User not found"), HttpStatus.NOT_FOUND);
        User user = userService.getByUsername(username).get();
        if (user.getPersona() == null)
            return new ResponseEntity(new Mensaje("This user doesn't have a profile"), HttpStatus.NOT_FOUND);
        Persona persona = personaService.get(user.getPersona().getId()).get();
        return new ResponseEntity(persona, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody PersonaDto personaDto, Principal principal) {
        if (!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("Persona not found"), HttpStatus.NOT_FOUND);
        if (!personaService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);        
        if (StringUtils.isBlank(personaDto.getFirstName()) || StringUtils.isBlank(personaDto.getLastName()))
            return new ResponseEntity(new Mensaje("Fields (firstName, lastName) should not be empty"), HttpStatus.BAD_REQUEST);
        
        Persona persona = personaService.get(id).get();
        persona.setFirstName(personaDto.getFirstName());
        persona.setLastName(personaDto.getLastName());
        persona.setPosition(personaDto.getPosition());
        persona.setLocation(personaDto.getLocation());
        persona.setAboutMe(personaDto.getAboutMe());
        persona.setBirthday(personaDto.getBirthday());
        persona.setBackImg(personaDto.getBackImg());
        persona.setProfileImg(personaDto.getProfileImg());
        
        personaService.save(persona);
        return new ResponseEntity(new Mensaje("Persona updated"), HttpStatus.OK);
    }
    
    @PutMapping("/update/image/{id}")
    public ResponseEntity<?> updateBackgroundImage(@PathVariable("id") int id, @RequestBody PersonaDto personaDto, Principal principal) {
        if (!personaService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!personaService.existsById(id))
            return new ResponseEntity(new Mensaje("Not found"), HttpStatus.NOT_FOUND);
        if (personaDto.getProfileImg() == null && personaDto.getBackImg() == null) {
            return new ResponseEntity(new Mensaje("No update. Send string"), HttpStatus.NOT_FOUND);
        }
        if (personaDto.getBackImg() == null) {
            Persona persona = personaService.get(id).get();
            persona.setProfileImg(personaDto.getProfileImg());
            personaService.save(persona);
        }
        if (personaDto.getProfileImg() == null) {
            Persona persona = personaService.get(id).get();
            persona.setBackImg(personaDto.getBackImg());
            personaService.save(persona);
        }        
        return new ResponseEntity(new Mensaje("Image updated"), HttpStatus.OK);
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
    
    @GetMapping("/exists/{username}")
    public ResponseEntity<?> existByUsername(@PathVariable("username") String username) {
        if (!userService.existsByUsername(username))
            return new ResponseEntity(false, HttpStatus.OK);
        User user = userService.getByUsername(username).get();
        if (user.getPersona() == null)
            return new ResponseEntity(false, HttpStatus.OK);
        return new ResponseEntity(true, HttpStatus.OK);
    }
    
    @GetMapping("/details/username/{usernameOrEmail}")
    public ResponseEntity<?> getUsernameByEmail(@PathVariable("usernameOrEmail") String usernameOrEmail) {
        if (!userService.existsByUsername(userService.getByUsernameOrEmail(usernameOrEmail).get().getUsername()))
            return new ResponseEntity(new Mensaje("User not found"), HttpStatus.NOT_FOUND);
        User user = userService.getByUsername(userService.getByUsernameOrEmail(usernameOrEmail).get().getUsername()).get();        
        String username = userService.getByUsernameOrEmail(usernameOrEmail).get().getUsername();
        ArrayList usernameArray = new ArrayList<>();
        usernameArray.add(username);
        return new ResponseEntity(usernameArray, HttpStatus.OK);
    }
}
