package com.bookmanager.Repositories;

import com.bookmanager.Models.BookTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookTitleRepository extends JpaRepository<BookTitle, Long> {


    List<BookTitle> findByTitle(String title);

    Optional<BookTitle> findByTitleAndAuthorAndPublisher(String title, String author, String publisher);

    BookTitle deleteBookTitleById(BookTitle booktitle);

}
