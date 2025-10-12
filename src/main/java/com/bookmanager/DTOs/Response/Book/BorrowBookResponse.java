package com.bookmanager.DTOs.Response.Book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowBookResponse {

    private Long userId;

    private String Name;

    private Long bookId;

    private String bookTitle;

    private LocalDate borrowDate;

    private LocalDate dueDate;

    private String status;

}
