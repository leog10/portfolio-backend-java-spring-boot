package com.leonardogilli.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonaDto {
    private String firstName;
    private String lastName;
    private String position;
    private String location;
    private String aboutMe;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthday;
    private String backImg;
    private String profileImg;
}
