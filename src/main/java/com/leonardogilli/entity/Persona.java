package com.leonardogilli.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Experience> experience = new ArrayList<>();
    
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Education> education = new ArrayList<>();
    
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Skill> skill = new ArrayList<>();
    
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Project> project = new ArrayList<>();

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
