package com.bookmanager.DTOs.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class IntrospectRequest {
    private String token;
}
