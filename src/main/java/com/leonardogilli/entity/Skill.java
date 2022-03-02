package com.leonardogilli.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardogilli.security.entity.User;
import javax.persistence.Column;
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
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(name="`current_number`")
    private int current;
    @Column(name="`max_number`")
    private int max;
    private int radius;
    private boolean semicircle;    
    private boolean rounded;
    private boolean clockwise;
    private int stroke;
    private String color;
    private String background;
    private int duration;
    private String animation;
    private int animationDelay;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Skill(String title, int current, int max, int radius, boolean semicircle, boolean rounded, boolean clockwise, int stroke, String color, String background, int duration, String animation, int animationDelay) {
        this.title = title;
        this.current = current;
        this.max = max;
        this.radius = radius;
        this.semicircle = semicircle;
        this.rounded = rounded;
        this.clockwise = clockwise;
        this.stroke = stroke;
        this.color = color;
        this.background = background;
        this.duration = duration;
        this.animation = animation;
        this.animationDelay = animationDelay;
    }
}
