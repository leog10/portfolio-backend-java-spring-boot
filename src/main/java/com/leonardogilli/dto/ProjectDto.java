package com.leonardogilli.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProjectDto {
    @NotBlank
    private String name;
    private String projectImg;
    private String description;
    private String startTime;
    private String endTime;
}
