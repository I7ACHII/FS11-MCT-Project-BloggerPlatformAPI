package com.geekster.BloggingPlatformAPI.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SignUpOutput {

    private boolean signUpStatus;
    private String signUpStatusMessage;

}
