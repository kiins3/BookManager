    package com.bookmanager.Models;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    @Builder
    @Entity
    @Table(name = "BorrowCards")
    public class LibraryCard {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private long id;

        @ManyToOne
        @JoinColumn(name = "userId", nullable = false)
        @JsonIgnore
        private User user;

        @ManyToOne
        @JoinColumn(name = "bookId", nullable = false)
        @JsonIgnore
        private Book book;

        @Column(name = "borrowDate")
        private LocalDate borrowDate;

        @Column(name = "dueDate")
        private LocalDate dueDate;

        @Column(name = "returnDate")
        private LocalDate returnDate;

        @Column(name = "compensationPaid")
        private boolean compensationPaid;

        @Column(name = "note")
        private String note;

        @Column(name = "status")
        private String status;
    }
