package com.leonardogilli.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardogilli.security.entity.User;
import java.time.LocalDate;
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
    private String firstName;
    private String lastName;
    private String position;
    private String location;
    private String aboutMe;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthday;
    private String backImg;
    private String profileImg;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique=true)
    @JsonIgnore
    private User user;

    public Persona(String firstName,String lastName, String position, String location, String aboutMe, LocalDate birthday, String backImg, String profileImg) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.location = location;
        this.aboutMe = aboutMe;
        this.birthday = birthday;
        this.backImg = backImg;
        this.profileImg = profileImg;
    }
}
