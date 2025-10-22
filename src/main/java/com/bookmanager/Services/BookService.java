package com.bookmanager.Services;

import com.bookmanager.DTOs.Request.Book.CreateBookRequest;
import com.bookmanager.DTOs.Request.Book.UpdateBookRequest;
import com.bookmanager.DTOs.Response.Book.BookUpdateResponse;
import com.bookmanager.DTOs.Response.Book.GetAllBookResponse;
import com.bookmanager.DTOs.Response.Book.GetBookByIdResponse;
import com.bookmanager.DTOs.Response.Book.GetBookByTitleResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Exception.RException;
import com.bookmanager.Helpers.TextTranf;
import com.bookmanager.Models.BookTitle;
import com.bookmanager.Repositories.BookRepository;
import com.bookmanager.Repositories.BookTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookmanager.Models.Book;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookTitleRepository bookTitleRepository;

    public List<Book> Bookcreate(CreateBookRequest request) {
        BookTitle bookTitle = bookTitleRepository.findById(request.getBookTitleId())
                .orElseThrow(() -> new RException(ErrorCode.BOOKTITLE_NOT_FOUND));

        int quantity = (request.getQuantity() == null || request.getQuantity() <= 0) ? 1 : request.getQuantity();
        bookTitle.setCopies(bookTitle.getCopies()+quantity);
        bookTitleRepository.save(bookTitle);

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Book book = new Book();
            book.setBookTitle(bookTitle);
            book.setStatus(request.getStatus());
            books.add(bookRepository.save(book));
        }
        return books;
    }

    public List<GetAllBookResponse> GetAllBooks(){
        List<Book> books = bookRepository.findAll();
        List<GetAllBookResponse> bookResponses = new ArrayList<>();
        for (Book book : books) {
            BookTitle bookTitle = book.getBookTitle();
            GetAllBookResponse bookResponse = new GetAllBookResponse(
                    book.getId(),
                    bookTitle.getTitle(),
                    bookTitle.getPublisher(),
                    bookTitle.getPublicationDate(),
                    book.getStatus(),
                    bookTitle.getCompensationCost(),
                    bookTitle.getAuthor()
            );
            bookResponses.add(bookResponse);
        }
        return bookResponses;
    }

    public GetBookByIdResponse findById(long id) {
        Book book = bookRepository.findById(id).
                orElseThrow(() -> new RException(ErrorCode.BOOK_NOT_FOUND));

        BookTitle bookTitle = book.getBookTitle();
        return new GetBookByIdResponse(
                book.getId(),
                bookTitle.getTitle(),
                bookTitle.getAuthor(),
                bookTitle.getPublisher(),
                bookTitle.getPublicationDate(),
                bookTitle.getCompensationCost(),
                book.getStatus()
        );

    }

    public BookUpdateResponse updateBook(UpdateBookRequest request) {
        Book book =  bookRepository.findById(request.getId()).
                orElseThrow(() -> new RException(ErrorCode.BOOK_NOT_FOUND));

        if (request.getStatus() != null) {
            book.setStatus(request.getStatus());
        }
        Book updatedBook = bookRepository.save(book);
        return BookUpdateResponse.builder()
                .title(updatedBook.getBookTitle().getTitle())
                .author(updatedBook.getBookTitle().getAuthor())
                .publisher(updatedBook.getBookTitle().getPublisher())
                .publishcationDate(updatedBook.getBookTitle().getPublicationDate())
                .status(updatedBook.getStatus())
                .compensationCost(updatedBook.getBookTitle().getCompensationCost())
                .build();
    }

    public List<GetBookByTitleResponse> findByBookTitle_TitleUnsignedContaining(String title) {
        String normalizedTitle = TextTranf.removeVietnameseAccents(title.toLowerCase());
        List<Book> bookMatched = bookRepository.findByBookTitle_TitleUnsignedContaining(normalizedTitle);
        if (bookMatched.isEmpty()) {
            throw new RException(ErrorCode.BOOK_NOT_FOUND);
        }

        List<GetBookByTitleResponse> bookResponses = new ArrayList<>();

        for (Book book : bookMatched) {
            BookTitle bookTitle = book.getBookTitle();
            GetBookByTitleResponse bookResponse = new GetBookByTitleResponse(
                    book.getId(),
                    bookTitle.getTitle(),
                    bookTitle.getAuthor(),
                    bookTitle.getPublisher(),
                    bookTitle.getPublicationDate(),
                    bookTitle.getCompensationCost(),
                    book.getStatus()
            );
            bookResponses.add(bookResponse);
        }
        return bookResponses;
    }

    public ErrorCode deleteBookById(long id) {
        Book book = bookRepository.findById(id).
                orElseThrow(() -> new RException(ErrorCode.BOOK_NOT_FOUND));

        BookTitle bookTitle = book.getBookTitle();
        if (bookTitle.getCopies() > 0) {
            bookTitle.setCopies(bookTitle.getCopies() - 1);
            bookTitleRepository.save(bookTitle);
        }
        bookRepository.deleteById(id);
        return ErrorCode.BOOK_DELETED;
    }
}
