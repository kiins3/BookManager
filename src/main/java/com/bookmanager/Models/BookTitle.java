package com.bookmanager.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "BookTitle")
public class BookTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToMany(mappedBy = "bookTitles")
    private Set<Category> categories = new HashSet<>();

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publicationDate")
    private LocalDate publicationDate;

    @Column(name = "status")
    private String status;

    @Column(name = "compensationCost")
    private double compensationCost;

    @Column(name = "copies")
    private int copies;

    @Column(name = "titleUnsigned")
    private String titleUnsigned;
}
