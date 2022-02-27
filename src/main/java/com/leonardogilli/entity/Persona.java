package com.leonardogilli.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardogilli.security.entity.User;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Persona {    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private String position;
    private String location;
    private String aboutMe;
    private int age;
    private String email;
    private String backImg;
    private String profileImg;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Persona(String fullName, String position, String location, String aboutMe, int age, String email, String backImg, String profileImg) {
        this.fullName = fullName;
        this.position = position;
        this.location = location;
        this.aboutMe = aboutMe;
        this.age = age;
        this.email = email;
        this.backImg = backImg;
        this.profileImg = profileImg;
    }
}
