package com.bookmanager.Exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public enum ErrorCode {
    INVALID_TOKEN(400, "Invalid Token"),
    USER_CREATED(500,"User created"),
    USERNAME_EXISTED(501,"Username already exists"),
    USER_NOT_FOUND(502, "User not Found"),
    EMAIL_EXISTED(503,"Email already exists"),
    USER_DELETED(504,"User deleted"),
    USER_UPDATED(505,"User updated"),
    SUCCESS(506,"Success"),
    UNAUTHENTICATED(507,"Unauthenticated"),
    FORBIDDEN(508,"You are not allowed to perform this action"),
    LOGIN_SUCCESS(509,"Login successful"),
    LOGIN_FAILED(510,"Login failed"),
    BOOK_CREATED(600,"Book created"),
    BOOK_EXISTED(601,"Book already exists"),
    BOOK_NOT_FOUND(602, "Book not found"),
    BOOK_DELETED(603,"Book deleted"),
    BOOK_UPDATED(604,"Book updated"),
    BOOKTITLE_CREATED(700,"Book title created"),
    BOOKTITLE_EXISTED(701,"Book title already exists"),
    BOOKTITLE_NOT_FOUND(702,"Book title not found"),
    BOOKTITLE_UPDATED(703,"Book title updated"),
    BOOKTITLE_DELETED(704,"Book title deleted"),
    BOOK_NOT_AVAILABLE(800,"Book has already borrowed"),
    BORROW_BOOK_SUCCESS(801,"Book successfully borrowed"),
    BORROW_CARD_NOT_FOUND(802,"Borrow card not found"),
    RETURNED(900,"Book feturned"),
    OVERDUE(901,"Return book overdue"),
    DAMAGED(902,"Book damaged, need compensation"),
    INVALID(903, "Invalid operation"),
    COMPENSATION(904,"Compensation paid"),
    PAYMENT_FAILED(905,"Payment failed"),
    ;
    private int code;
    private String message;
}
