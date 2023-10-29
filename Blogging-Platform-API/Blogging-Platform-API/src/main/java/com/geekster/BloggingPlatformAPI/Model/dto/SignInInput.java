package com.geekster.BloggingPlatformAPI.Model.dto;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInInput {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Email should end with @gmail.com")
    private String email;
    private String password;
}
