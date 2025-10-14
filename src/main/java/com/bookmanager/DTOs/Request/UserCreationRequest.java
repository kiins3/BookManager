package com.bookmanager.DTOs.Request;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserCreationRequest {
    private String name;

    private String gender;

    private LocalDate dob;

    private String email;

    private String username;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least 8 characters, 1 uppercase, 1 lowercase, 1 number, 1 special character"
    )
    private String password;

    private String phone;

    private String role;

    private String address;

    private String status;

    private int violationCount;
}
