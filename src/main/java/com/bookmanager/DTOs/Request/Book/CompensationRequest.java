package com.bookmanager.DTOs.Request.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CompensationRequest {
    private Long cardId;

    private double compensation;

}
