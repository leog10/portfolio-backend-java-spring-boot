package com.leonardogilli.security.service;

import com.leonardogilli.security.entity.User;
import com.leonardogilli.security.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    public List<User> list() {
        return userRepository.findAll();
    }
    
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<User> getByTokenPassword(String tokenPassword) {
        return userRepository.findByTokenPassword(tokenPassword);
    }
    
    public Optional<User> getByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.existsByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }    
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public void save(User user) {
        userRepository.save(user);
    }
}
