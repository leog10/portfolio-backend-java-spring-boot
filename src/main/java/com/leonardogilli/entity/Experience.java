package com.leonardogilli.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardogilli.security.entity.User;
import javax.persistence.Entity;
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
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String position;
    private String company;
    private String img;
    private String mode;
    private String startTime;
    private String endTime;
    private String timeAtPosition;
    private String location;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Experience(String position, String company, String img, String mode, String startTime, String endTime, String timeAtPosition, String location) {
        this.position = position;
        this.company = company;
        this.img = img;
        this.mode = mode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeAtPosition = timeAtPosition;
        this.location = location;
    }
}
