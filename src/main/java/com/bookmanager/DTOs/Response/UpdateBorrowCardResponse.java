package com.bookmanager.DTOs.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateBorrowCardResponse {
    private Long id;

    private String status;
}
