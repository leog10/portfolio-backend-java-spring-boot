package com.leonardogilli.emailpassword.controller;

import com.leonardogilli.dto.Mensaje;
import com.leonardogilli.emailpassword.dto.ChangePasswordDTO;
import com.leonardogilli.emailpassword.dto.EmailValuesDTO;
import com.leonardogilli.emailpassword.service.EmailService;
import com.leonardogilli.security.entity.User;
import com.leonardogilli.security.service.UserService;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email-password")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {
    
    @Value("${spring.mail.username}")
    private String mailFrom;
    
    @Value("${mail.subject}")
    private String subject;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    UserService userService;
    
    @Autowired
    EmailService emailService;
    
    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailValuesDTO dto) {
        Optional<User> userOpt = userService.getByUsernameOrEmail(dto.getMailTo());
        if (!userOpt.isPresent())
            return new ResponseEntity(new Mensaje("El usuario o email ingresado no existe"), HttpStatus.NOT_FOUND);
        User user = userOpt.get();
        dto.setMailFrom(mailFrom);
        dto.setMailTo(user.getEmail());
        dto.setSubject(subject);
        dto.setUsername(user.getUsername());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        dto.setTokenPassword(tokenPassword);
        user.setTokenPassword(tokenPassword);
        userService.save(user);
        emailService.sendEmail(dto);
        return new ResponseEntity(new Mensaje("Te hemos enviado un email"), HttpStatus.OK);
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Incorrect data or invalid email"), HttpStatus.BAD_REQUEST);
        if (!dto.getPassword().equals(dto.getConfirmPassword()))
            return new ResponseEntity(new Mensaje("Passwords doesn't match"), HttpStatus.BAD_REQUEST);
        Optional<User> userOpt = userService.getByTokenPassword(dto.getTokenPassword());
        if (!userOpt.isPresent())
            return new ResponseEntity(new Mensaje("El enlace a caducado o ya has cambiado tu contraseña. Si no la recuerdas prueba volver a recuperar tu contraseña"), HttpStatus.NOT_FOUND);
        User user = userOpt.get();
        String newPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(newPassword);
        user.setTokenPassword(null);
        userService.save(user);
        return new ResponseEntity(new Mensaje("Contraseña actualizada"), HttpStatus.OK);                
    }
}
