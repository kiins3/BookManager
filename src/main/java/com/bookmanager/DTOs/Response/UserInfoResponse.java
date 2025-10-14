package com.bookmanager.DTOs.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {
    private Long id;

    private String name;

    private String gender;

    private LocalDate dob;

    private String email;

    private String username;

    private String phone;

    private String role;

    private String address;

    private int violationCount;

    private String status;
}
