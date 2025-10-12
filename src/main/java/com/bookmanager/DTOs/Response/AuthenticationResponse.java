package com.bookmanager.DTOs.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthenticationResponse {
    private boolean authenticated;
    private String token;
}
