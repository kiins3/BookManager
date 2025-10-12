package com.bookmanager.DTOs.Request.Book;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateBookRequest {

    private Long bookTitleId;

    private String status;

    private Integer quantity;
}
