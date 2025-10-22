package com.bookmanager.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Category")
public class Category {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "Cate_BookT",
            joinColumns = @JoinColumn(name = "cateId"),
            inverseJoinColumns = @JoinColumn(name = "bookTId")
    )
    private Set<BookTitle> bookTitles = new HashSet<>();

    @Column(name = "category")
    private String categoryName;
}
