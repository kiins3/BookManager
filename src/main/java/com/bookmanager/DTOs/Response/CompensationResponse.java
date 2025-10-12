package com.bookmanager.DTOs.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompensationResponse {
    private Long userId;

    private Long bookId;

    private String name;

    private String bookTitle;

    private boolean compensationPaid;

    private String status;

    private String note;
}
