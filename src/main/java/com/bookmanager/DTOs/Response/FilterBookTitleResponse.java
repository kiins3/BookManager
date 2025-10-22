package com.bookmanager.DTOs.Response;

import com.bookmanager.Models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterBookTitleResponse {
    private Long id;

    private String title;

    private String author;

    private String publisher;

    private LocalDate publicationDate;

    private Double compensationCost;

    private String status;

    private Set<CategoryResponse> categories;
}
