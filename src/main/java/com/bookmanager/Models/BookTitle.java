package com.bookmanager.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

}
