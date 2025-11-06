package com.bookmanager.Exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public enum ErrorCode {
    INVALID_TOKEN(400, "Invalid Token"),
    USER_CREATED(200,"User created"),
    USERNAME_EXISTED(501,"Username already exists"),
    USER_NOT_FOUND(502, "User not found"),
    EMAIL_EXISTED(503,"Email already exists"),
    USER_DELETED(200,"User deleted"),
    USER_UPDATED(200,"User updated"),
    SUCCESS(200,"Success"),
    UNAUTHENTICATED(507,"Unauthenticated"),
    FORBIDDEN(508,"You are not allowed to perform this action"),
    LOGIN_SUCCESS(200,"Login successful"),
    LOGIN_FAILED(510,"Login failed"),
    BOOK_CREATED(200,"Book created"),
    BOOK_EXISTED(601,"Book already exist"),
    BOOK_NOT_FOUND(602, "Book not found"),
    BOOK_DELETED(200,"Book deleted"),
    BOOK_UPDATED(200,"Book updated"),
    BOOKTITLE_CREATED(200,"Book title created"),
    BOOKTITLE_EXISTED(701,"Book title already exists"),
    BOOKTITLE_NOT_FOUND(702,"Book title not found"),
    BOOKTITLE_UPDATED(200,"Book title updated"),
    BOOKTITLE_DELETED(200,"Book title deleted"),
    BOOK_NOT_AVAILABLE(800,"Book has already borrowed"),
    BORROW_BOOK_SUCCESS(200,"Book successfully borrowed"),
    BORROW_CARD_NOT_FOUND(802,"Borrow card not found"),
    UNRETURNED_BOOK(803,"Books not returned or compensated"),
    CARD_DELETED(200,"Borrow card deleted"),
    NOT_ALLOWED(805,"Wrong ID, you are not allowed to borrow book for others"),
    RETURNED(200,"Book returned"),
    OVERDUE(200,"Return book overdue"),
    DAMAGED(200,"Book damaged, need compensation"),
    INVALID(903, "Invalid operation"),
    COMPENSATION(904,"Compensation paid"),
    PAYMENT_FAILED(905,"Payment failed"),
    BACK_MONEY(200,"Payment successful, change has been transferred to your wallet"),
    ;
    private int code;
    private String message;
}
