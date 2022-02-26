package com.leonardogilli.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExperienceDto {
    @NotBlank
    private String position;
    @NotBlank
    private String company;
    private String img;
    @NotBlank
    private String mode;
    private String startTime;
    private String endTime;
    private String timeAtPosition;
    private String location;
}
