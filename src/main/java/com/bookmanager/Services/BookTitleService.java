package com.bookmanager.Services;

import com.bookmanager.DTOs.Request.BookTitle.BookTitleCreationRequest;
import com.bookmanager.DTOs.Request.BookTitle.BookTitleUpdateRequest;
import com.bookmanager.DTOs.Response.GetBookTitleResponse;
import com.bookmanager.DTOs.Response.UpdateBookTitleResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Exception.RException;
import com.bookmanager.Helpers.TextTranf;
import com.bookmanager.Mapper.BookTitleMapper;
import com.bookmanager.Models.Book;
import com.bookmanager.Models.BookTitle;
import com.bookmanager.Repositories.BookRepository;
import com.bookmanager.Repositories.BookTitleRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookTitleService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookTitleRepository bookTitleRepository;
    @Autowired
    private BookTitleMapper bookTitleMapper;

    public Optional<BookTitle> getBookTitleById(long id) {
        BookTitle bookTitle = bookTitleRepository.findById(id)
                .orElseThrow(() -> new RException(ErrorCode.BOOKTITLE_NOT_FOUND));
        return bookTitleRepository.findById(id);
    }

    public List<GetBookTitleResponse> getAllBookTitles() {
        List<BookTitle> allTitles = bookTitleRepository.findAll();
        return bookTitleMapper.toGetBookTitleResponses(allTitles);
    }

    public List<GetBookTitleResponse> getBookTitleByTitle(String title) {
        String normalizedTitle = TextTranf.removeVietnameseAccents(title.toLowerCase());
        List<BookTitle> bookTitleMatched = bookTitleRepository.findByTitleUnsignedContaining(normalizedTitle);

        if (bookTitleMatched.isEmpty()) {
            throw new RException(ErrorCode.BOOKTITLE_NOT_FOUND);
        }

        return bookTitleMapper.toGetBookTitleResponses(bookTitleMatched);
    }

    public BookTitle createBookTitle(BookTitleCreationRequest request) {
        Optional<BookTitle> existing = bookTitleRepository.findByTitleAndAuthorAndPublisher(
                request.getTitle(), request.getAuthor(), request.getPublisher()
        );

        if (existing.isPresent()) {
            throw new RException(ErrorCode.BOOKTITLE_EXISTED);
        }

        BookTitle bookTitle = new BookTitle();
        bookTitle.setTitle(request.getTitle());
        bookTitle.setAuthor(request.getAuthor());
        bookTitle.setCompensationCost(request.getCompensationCost());
        bookTitle.setPublicationDate(request.getPublicationDate());
        bookTitle.setPublisher(request.getPublisher());

        int copies = (request.getCopies() == 0 || request.getCopies() <= 0) ? 1 : request.getCopies();
        bookTitle.setCopies(copies);

        BookTitle savedBookTitle = bookTitleRepository.save(bookTitle);

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < copies; i++) {
            Book book = new Book();
            book.setBookTitle(savedBookTitle);
            book.setStatus("AVAILABLE");
            books.add(book);
        }
        bookRepository.saveAll(books);

        return savedBookTitle;
    }

    public UpdateBookTitleResponse updateBookTitle(BookTitleUpdateRequest request){
        BookTitle bookTitleUpdate = bookTitleRepository.findById(request.getId())
                .orElseThrow(() -> new RException(ErrorCode.BOOKTITLE_NOT_FOUND));

        bookTitleMapper.UpdateBookTitle(request,bookTitleUpdate);

        bookTitleRepository.save(bookTitleUpdate);

        return bookTitleMapper.toUpdateBookTitleResponse(bookTitleUpdate);
    }

    public ErrorCode deleteBookTitleById(long id) {
        BookTitle booktitle = bookTitleRepository.findById(id)
                .orElseThrow(() -> new RException(ErrorCode.BOOKTITLE_NOT_FOUND));

        List<Book>  books = bookRepository.findByBookTitleId(id);
        bookRepository.deleteAll(books);
        bookTitleRepository.delete(booktitle);
        return ErrorCode.BOOKTITLE_DELETED;
    }
}
