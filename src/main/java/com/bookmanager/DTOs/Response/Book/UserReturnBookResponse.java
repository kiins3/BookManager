package com.bookmanager.DTOs.Response.Book;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReturnBookResponse {
    private Long userId;

    private String name;

    private Long bookId;

    private String title;

    private String note;

    private LocalDate returnDate;
}
