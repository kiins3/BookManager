package com.bookmanager.Repositories;

import com.bookmanager.Models.Book;
import com.bookmanager.Models.BookTitle;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByBookTitle_Title(String title);

    Book deleteById(long id);

    List<Book> id(long id);

    List<Book> findByBookTitleId(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Book> findFirstByBookTitleAndStatus(BookTitle bookTitle, String available);

}
