package com.bookmanager.Services;

import com.bookmanager.DTOs.Request.Book.BorrowBookRequest;
import com.bookmanager.DTOs.Request.Book.BookCompensationRequest;
import com.bookmanager.DTOs.Request.Book.ReturnBookRequest;
import com.bookmanager.DTOs.Request.Book.UserReturnBookRequest;
import com.bookmanager.DTOs.Response.Book.BorrowBookResponse;
import com.bookmanager.DTOs.Response.Book.UserReturnBookResponse;
import com.bookmanager.DTOs.Response.CompensationResponse;
import com.bookmanager.DTOs.Response.ListBorrowCardResponse;
import com.bookmanager.DTOs.Response.Book.ReturnBookResponse;
import com.bookmanager.DTOs.Response.UserBorrowHistoryResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Exception.RException;
import com.bookmanager.Models.Book;
import com.bookmanager.Models.BookTitle;
import com.bookmanager.Models.LibraryCard;
import com.bookmanager.Models.User;
import com.bookmanager.Repositories.BookRepository;
import com.bookmanager.Repositories.BookTitleRepository;
import com.bookmanager.Repositories.LibraryCardRepository;
import com.bookmanager.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryCardService {

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookTitleRepository bookTitleRepository;

    @Transactional
    public BorrowBookResponse borrowBook(BorrowBookRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RException(ErrorCode.USER_NOT_FOUND));

        List<BookTitle> bookTitles = bookTitleRepository.findByTitle(request.getTitle());
                if (bookTitles.isEmpty()) {
                    throw new RException(ErrorCode.BOOKTITLE_NOT_FOUND);
                }
        BookTitle bookTitle = bookTitles.get(0);

        Book book = bookRepository.findFirstByBookTitleAndStatus(bookTitle,"AVAILABLE")
                .orElseThrow(() -> new RException(ErrorCode.BOOK_NOT_AVAILABLE));

        if (!"AVAILABLE".equalsIgnoreCase(book.getStatus())) {
            throw new RException(ErrorCode.BOOK_NOT_AVAILABLE);
        }
        book.setStatus("BORROWING");
        bookRepository.save(book);
        LibraryCard libraryCard = LibraryCard.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .status("BORROWING")
                .build();
        libraryCardRepository.save(libraryCard);
        return BorrowBookResponse.builder()
                .userId(user.getId())
                .Name(user.getName())
                .bookId(book.getId())
                .bookTitle(bookTitle.getTitle())
                .borrowDate(libraryCard.getBorrowDate())
                .dueDate(libraryCard.getDueDate())
                .status(libraryCard.getStatus())
                .build();
    }

    public List<ListBorrowCardResponse> getListBorrowCardResponse() {
        return libraryCardRepository.findAll()
                .stream()
                .map(card -> ListBorrowCardResponse.builder()
                        .name(card.getUser() != null ? card.getUser().getName() : null)
                        .title(
                                card.getBook() != null && card.getBook().getBookTitle() != null
                                        ? card.getBook().getBookTitle().getTitle()
                                        : null
                        )
                        .borrowDate(card.getBorrowDate())
                        .dueDate(card.getDueDate())
                        .returnDate(card.getReturnDate())
                        .status(card.getStatus())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public UserReturnBookResponse userReturnBook(UserReturnBookRequest request) {
        LibraryCard libraryCard = libraryCardRepository.findById(request.getCardId())
                .orElseThrow(() -> new RException(ErrorCode.BORROW_CARD_NOT_FOUND));

        if (!libraryCard.getStatus().equals("BORROWING")) {
            throw new RException(ErrorCode.INVALID);
        }

        UserReturnBookResponse response = new UserReturnBookResponse().builder()
                .userId(libraryCard.getUser().getId())
                .name(libraryCard.getUser().getName())
                .bookId(libraryCard.getBook().getId())
                .title(libraryCard.getBook().getBookTitle().getTitle())
                .returnDate(request.getReturnDate())
                .note(request.getNote())
                .build();

        libraryCard.setStatus("PENDING_RETURN");
        libraryCard.setNote(request.getNote());
        libraryCard.setReturnDate(request.getReturnDate());
        libraryCardRepository.save(libraryCard);

        return response;
    }

    public ReturnBookResponse returnBook(ReturnBookRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RException(ErrorCode.USER_NOT_FOUND));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RException(ErrorCode.BOOK_NOT_FOUND));

        LibraryCard libraryCard = libraryCardRepository.findById(request.getCardId())
                .orElseThrow(() -> new RException(ErrorCode.BORROW_CARD_NOT_FOUND));

        boolean isOverDue = request.getReturnDate().isAfter(libraryCard.getDueDate());
        boolean isDamaged = request.getStatus().equals("DAMAGED");

        if (isOverDue || isDamaged) {
            user.setViolationCount(user.getViolationCount() + 1);
            if (user.getViolationCount() >= 3) {
                user.setStatus("BANNED");
            }
            userRepository.save(user);
        }


        if (isDamaged) {
            book.setStatus("DAMAGED");
            libraryCard.setStatus("NEED_COMPENSATION");
            libraryCard.setCompensationPaid(false);
            libraryCard.setNote("Book damaged, pending compensation");
        } else if (isOverDue) {
            book.setStatus("AVAILABLE");
            libraryCard.setStatus("OVERDUE");
            libraryCard.setCompensationPaid(true);
            libraryCard.setNote("Returned late, no damage");
        } else {
            book.setStatus("AVAILABLE");
            libraryCard.setStatus("RETURNED");
            libraryCard.setCompensationPaid(true);
            libraryCard.setNote("");
        }

        libraryCard.setReturnDate(request.getReturnDate());
        bookRepository.save(book);
        libraryCardRepository.save(libraryCard);

        return ReturnBookResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .bookId(book.getId())
                .bookTitle(book.getBookTitle().getTitle())
                .borrowDate(libraryCard.getBorrowDate())
                .returnDate(libraryCard.getReturnDate())
                .status(libraryCard.getStatus())
                .compensationCost(book.getBookTitle().getCompensationCost())
                .note(libraryCard.getNote())
                .build();
    }

    public CompensationResponse payCompensation(BookCompensationRequest request) {
        LibraryCard libraryCard = libraryCardRepository.findByUserIdAndBookId(request.getUserId(), request.getBookId())
                .orElseThrow(() -> new RException(ErrorCode.BORROW_CARD_NOT_FOUND));

        if(!"NEED_COMPENSATION".equals(libraryCard.getStatus())){
            throw new RException(ErrorCode.INVALID);
        }

        Book book = libraryCard.getBook();
        BookTitle bookTitle = book.getBookTitle();
        if (bookTitle.getCompensationCost() != request.getCompensation())
            throw new RException(ErrorCode.PAYMENT_FAILED);

        libraryCard.setCompensationPaid(true);
        libraryCard.setStatus("RETURN BOOK AND PAID");
        libraryCard.setNote("BOOK WAS DAMAGED ");
        libraryCardRepository.save(libraryCard);

        book.setStatus("UNAVAILABLE");
        bookRepository.save(book);

        User user = libraryCard.getUser();
        if (user.getViolationCount() > 0) {
            user.setViolationCount(user.getViolationCount() - 1);
        }
        userRepository.save(user);

        return CompensationResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .bookId(book.getId())
                .bookTitle(book.getBookTitle().getTitle())
                .compensationPaid(libraryCard.isCompensationPaid())
                .status(libraryCard.getStatus())
                .note(libraryCard.getNote())
                .build();
    }

    public List<UserBorrowHistoryResponse> getBorrowHistoryByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RException(ErrorCode.USER_NOT_FOUND));

        return libraryCardRepository.findByUser(user)
                .stream()
                .map(card -> UserBorrowHistoryResponse.builder()
                        .cardId(card.getId())
                        .bookId(card.getBook().getId())
                        .bookTitle(card.getBook().getBookTitle().getTitle())
                        .borrowDate(card.getBorrowDate())
                        .dueDate(card.getDueDate())
                        .returnDate(card.getReturnDate())
                        .status(card.getStatus())
                        .build())
                .toList();
    }

}
