package com.bookmanager.DTOs.Request.Book;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BorrowBookRequest {
    private long userId;

    private String title;

}
