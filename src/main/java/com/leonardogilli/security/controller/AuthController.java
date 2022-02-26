package com.leonardogilli.security.controller;

import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.security.dto.JwtDto;
import com.leonardogilli.security.dto.LoginUser;
import com.leonardogilli.security.dto.NewUser;
import com.leonardogilli.security.entity.Rol;
import com.leonardogilli.security.entity.User;
import com.leonardogilli.security.enums.RolName;
import com.leonardogilli.security.jwt.JwtProvider;
import com.leonardogilli.security.service.RolService;
import com.leonardogilli.security.service.UserService;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserService userService;
    
    @Autowired
    RolService rolService;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @PostMapping("/new")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("incorrect data or invalid email"), HttpStatus.BAD_REQUEST);
        if (userService.existsByUsername(newUser.getUsername()))
            return new ResponseEntity(new Mensaje("this username is already taken"), HttpStatus.BAD_REQUEST);
        if (userService.existsByEmail(newUser.getEmail()))
            return new ResponseEntity(new Mensaje("this email is already taken"), HttpStatus.BAD_REQUEST);
        User user = new User(
                newUser.getName(), 
                newUser.getUsername(), 
                newUser.getEmail(), 
                passwordEncoder.encode(newUser.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROLE_USER).get());
        if (newUser.getRoles().contains("admin"))
            roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity(new Mensaje("User created"), HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("fields with errors"), HttpStatus.BAD_REQUEST);
        if (!userService.existsByUsername(loginUser.getUsername()))
            return new ResponseEntity(new Mensaje("fields with errors"), HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        JwtDto jwtDto = new JwtDto(jwt);
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refreshToken(@RequestBody JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        JwtDto jwt = new JwtDto(token);
        return new ResponseEntity(jwt, HttpStatus.OK);
    }
}
