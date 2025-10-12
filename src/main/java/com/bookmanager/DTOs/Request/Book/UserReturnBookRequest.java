package com.bookmanager.DTOs.Request.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReturnBookRequest {
    private Long cardId;

    private String note;

    private LocalDate returnDate;
}
