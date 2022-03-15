package com.leonardogilli.emailpassword.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailValuesDTO {
    private String mailFrom;
    private String mailTo;
    private String subject;
    private String username;
    private String tokenPassword;
}
