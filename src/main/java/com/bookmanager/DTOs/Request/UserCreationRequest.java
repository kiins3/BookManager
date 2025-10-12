package com.bookmanager.DTOs.Request;

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

    private String password;

    private String phone;

    private String role;

    private String address;

    private String status;
}
