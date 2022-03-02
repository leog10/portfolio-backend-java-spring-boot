package com.leonardogilli.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SkillDto {
    @NotBlank
    private String title;
    @NotBlank
    @Min(0)
    @Max(110)
    private int current;
    @NotBlank
    @Min(100)
    @Max(100)
    private int max;
    @NotBlank
    @Min(70)
    @Max(150)
    private int radius;
    @NotBlank
    private boolean semicircle;
    @NotBlank
    private boolean rounded;    
    @NotBlank
    private boolean clockwise;
    @NotBlank
    @Min(5)
    @Max(50)
    private int stroke;
    @NotBlank
    private String color;
    @NotBlank
    private String background;
    @NotBlank
    @Min(1)
    @Max(5000)
    private int duration;
    @NotBlank
    private String animation;
    @NotBlank
    @Min(0)
    @Max(5000)
    private int animationDelay;   
}
