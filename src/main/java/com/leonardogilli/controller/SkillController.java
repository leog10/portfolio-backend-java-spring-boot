package com.leonardogilli.controller;

import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.dto.SkillDto;
import com.leonardogilli.entity.Skill;
import com.leonardogilli.security.entity.User;
import com.leonardogilli.security.service.UserService;
import com.leonardogilli.service.SkillService;
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
@RequestMapping("/api/skill")
@CrossOrigin(origins = "http://localhost:4200")
public class SkillController {
    
    @Autowired
    SkillService skillService;
    
    @Autowired
    UserService userService;
    
    @GetMapping("/list")
    public ResponseEntity<List<Skill>> list() {
        List<Skill> list = skillService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Skill> getById(@PathVariable("id") int id) {
        if(!skillService.existsById(id))
            return new ResponseEntity(new Mensaje("Skill not found"), HttpStatus.NOT_FOUND);
        Skill skill = skillService.get(id).get();
        return new ResponseEntity(skill, HttpStatus.OK);
    }
    
    @RequestMapping("/create")
    public ResponseEntity<?> create(@RequestBody SkillDto skillDto, Principal principal) {
        if(StringUtils.isBlank(skillDto.getTitle()))
            return new ResponseEntity(new Mensaje("title can not be empty"), HttpStatus.BAD_REQUEST);
        if(skillDto.getCurrentNumber() < 0 || skillDto.getCurrentNumber() > 110)
            return new ResponseEntity(new Mensaje("current must be between 0 and 100"), HttpStatus.BAD_REQUEST);
        if(skillDto.getMaxNumber() != 100)
            return new ResponseEntity(new Mensaje("max must be 100"), HttpStatus.BAD_REQUEST);
        if(skillDto.getRadius() < 70 || skillDto.getRadius() > 150)
            return new ResponseEntity(new Mensaje("radius must be between 70 and 150"), HttpStatus.BAD_REQUEST);
        if(skillDto.getRadius() < 70 || skillDto.getRadius() > 150)
            return new ResponseEntity(new Mensaje("radius must be between 70 and 150"), HttpStatus.BAD_REQUEST);
        if(skillDto.getStroke() < 5 || skillDto.getStroke() > 50)
            return new ResponseEntity(new Mensaje("stroke must be between 5 and 50"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(skillDto.getColor()))
            return new ResponseEntity(new Mensaje("color can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(skillDto.getBackground()))
            return new ResponseEntity(new Mensaje("background can not be empty"), HttpStatus.BAD_REQUEST);
        if(skillDto.getDuration() < 1 || skillDto.getDuration() > 5000)
            return new ResponseEntity(new Mensaje("duration must be between 1 and 5000"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(skillDto.getAnimation()))
            return new ResponseEntity(new Mensaje("animation can not be empty"), HttpStatus.BAD_REQUEST);
        if(skillDto.getAnimationDelay() < 0 || skillDto.getAnimationDelay() > 5000)
            return new ResponseEntity(new Mensaje("animation delay must be between 0 and 5000"), HttpStatus.BAD_REQUEST);
        
        Skill skill = new Skill(
                skillDto.getTitle(), 
                skillDto.getCurrentNumber(), 
                skillDto.getMaxNumber(), 
                skillDto.getRadius(), 
                skillDto.isSemicircle(), 
                skillDto.isRounded(), 
                skillDto.isResponsive(), 
                skillDto.isClockwise(), 
                skillDto.getStroke(), 
                skillDto.getColor(), 
                skillDto.getBackground(), 
                skillDto.getDuration(), 
                skillDto.getAnimation(), 
                skillDto.getAnimationDelay());
        
        User user = userService.getByUsername(principal.getName()).get();
        skill.setUser(user);
        
        skillService.save(skill);
        return new ResponseEntity(new Mensaje("Skill created"), HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody SkillDto skillDto, Principal principal) {
        if (!skillService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!skillService.existsById(id))
            return new ResponseEntity(new Mensaje("Skill not found"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(skillDto.getTitle()))
            return new ResponseEntity(new Mensaje("title can not be empty"), HttpStatus.BAD_REQUEST);
        if(skillDto.getCurrentNumber() < 0 || skillDto.getCurrentNumber() > 110)
            return new ResponseEntity(new Mensaje("current must be between 0 and 100"), HttpStatus.BAD_REQUEST);
        if(skillDto.getMaxNumber() != 100)
            return new ResponseEntity(new Mensaje("max must be 100"), HttpStatus.BAD_REQUEST);
        if(skillDto.getRadius() < 70 || skillDto.getRadius() > 150)
            return new ResponseEntity(new Mensaje("radius must be between 70 and 150"), HttpStatus.BAD_REQUEST);
        if(skillDto.getRadius() < 70 || skillDto.getRadius() > 150)
            return new ResponseEntity(new Mensaje("radius must be between 70 and 150"), HttpStatus.BAD_REQUEST);
        if(skillDto.getStroke() < 5 || skillDto.getStroke() > 50)
            return new ResponseEntity(new Mensaje("stroke must be between 5 and 50"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(skillDto.getColor()))
            return new ResponseEntity(new Mensaje("color can not be empty"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(skillDto.getBackground()))
            return new ResponseEntity(new Mensaje("background can not be empty"), HttpStatus.BAD_REQUEST);
        if(skillDto.getDuration() < 1 || skillDto.getDuration() > 5000)
            return new ResponseEntity(new Mensaje("duration must be between 1 and 5000"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(skillDto.getAnimation()))
            return new ResponseEntity(new Mensaje("animation can not be empty"), HttpStatus.BAD_REQUEST);
        if(skillDto.getAnimationDelay() < 0 || skillDto.getAnimationDelay() > 5000)
            return new ResponseEntity(new Mensaje("animation delay must be between 0 and 5000"), HttpStatus.BAD_REQUEST);
        
        Skill skill = skillService.get(id).get();
        skill.setCurrentNumber(skillDto.getCurrentNumber());
        skill.setMaxNumber(skillDto.getMaxNumber());
        skill.setRadius(skillDto.getRadius());
        skill.setSemicircle(skillDto.isSemicircle());
        skill.setRounded(skillDto.isRounded());
        skill.setResponsive(skillDto.isResponsive());
        skill.setClockwise(skillDto.isClockwise());
        skill.setStroke(skillDto.getStroke());
        skill.setColor(skillDto.getColor());
        skill.setBackground(skillDto.getBackground());
        skill.setDuration(skillDto.getDuration());
        skill.setAnimation(skillDto.getAnimation());
        skill.setAnimationDelay(skillDto.getAnimationDelay());
        
        skillService.save(skill);
        return new ResponseEntity(new Mensaje("Skill updated"), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id, Principal principal) {
        if (!skillService.get(id).get().getUser().getUsername()
                .equals(principal.getName()))
            return new ResponseEntity(new Mensaje("Not allowed to do that"), HttpStatus.FORBIDDEN);
        if (!skillService.existsById(id))
            return new ResponseEntity(new Mensaje("Skill not found"), HttpStatus.NOT_FOUND);
        skillService.delete(id);
        return new ResponseEntity(new Mensaje("Skill deleted"), HttpStatus.OK);
    }
}
