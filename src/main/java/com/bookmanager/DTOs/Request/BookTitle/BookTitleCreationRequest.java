package com.bookmanager.DTOs.Request.BookTitle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BookTitleCreationRequest {

        private String title;

        private String author;

        private String publisher;

        private LocalDate publicationDate;

        private Double compensationCost;

        private String status;

        private int copies;
}

