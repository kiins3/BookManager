package com.bookmanager.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateUserResponse {
    private Long id;

    private String name;

    private String gender;

    private String address;

    private String email;

    private String phone;

    private String password;

    private String role;

    private String status;

    private LocalDate dob;

    private String username;

    private int violationCount;

}
