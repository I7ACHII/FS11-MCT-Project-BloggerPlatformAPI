package com.geekster.BloggingPlatformAPI.Model;


import com.geekster.BloggingPlatformAPI.Model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotBlank
    private String username;
    private String userFullName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Email should end with @gmail.com")
    @Column(unique = true)
    private String userEmail;

    @NotBlank
    private String userPassword;

    @Enumerated(EnumType.STRING)
    private Gender gender;



}
