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
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String school;
    private String title;
    private String img;
    private String career;
    private String startTime;
    private String endTime;
    private String location;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Education(String school, String title, String img, String career, String startTime, String endTime, String location) {
        this.school = school;
        this.title = title;
        this.img = img;
        this.career = career;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }
}
