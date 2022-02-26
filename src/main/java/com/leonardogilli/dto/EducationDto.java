package com.leonardogilli.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EducationDto {
    @NotBlank
    private String school;
    @NotBlank
    private String title;
    private String img;
    private String career;
    private String startTime;
    private String endTime;
    @NotBlank
    private String location;
}
