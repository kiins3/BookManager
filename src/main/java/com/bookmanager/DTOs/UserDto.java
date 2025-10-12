package com.bookmanager.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
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
