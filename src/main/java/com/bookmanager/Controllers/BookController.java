package com.bookmanager.Controllers;

import com.bookmanager.DTOs.Request.Book.CreateBookRequest;
import com.bookmanager.DTOs.Request.Book.UpdateBookRequest;
import com.bookmanager.DTOs.Response.*;
import com.bookmanager.DTOs.Response.Book.BookUpdateResponse;
import com.bookmanager.DTOs.Response.Book.GetAllBookResponse;
import com.bookmanager.DTOs.Response.Book.GetBookByIdResponse;
import com.bookmanager.DTOs.Response.Book.GetBookByTitleResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Models.Book;
import com.bookmanager.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/post")
    ResponseEntity<BaseResponse<List<Book>>> Bookcreate(@RequestBody CreateBookRequest request) {
        BaseResponse <List<Book>> response = new BaseResponse<>();
        response.setResult(bookService.Bookcreate(request));
        response.setCode(ErrorCode.BOOK_CREATED.getCode());
        response.setMessage(ErrorCode.BOOK_CREATED.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getallbooks")
    ResponseEntity<BaseResponse<List<GetAllBookResponse>>> getAllBook() {
        BaseResponse<List<GetAllBookResponse>> response = new BaseResponse<>();
        response.setResult(bookService.GetAllBooks());
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getbookbyid/{id}")
    ResponseEntity<BaseResponse<GetBookByIdResponse>> getBookById(@PathVariable long id) {
        BaseResponse<GetBookByIdResponse> response = new BaseResponse<>();
        response.setResult(bookService.findById(id));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getbookbytitle/{title}")
    ResponseEntity<BaseResponse<List<GetBookByTitleResponse>>> findByBookTitle_Title(@PathVariable String title) {
        BaseResponse<List<GetBookByTitleResponse>> response = new BaseResponse<>();
        response.setResult(bookService.findByBookTitle_Title(title));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatebook")
     ResponseEntity<BaseResponse<BookUpdateResponse>> updateBook(@RequestBody UpdateBookRequest request) {
        BaseResponse<BookUpdateResponse> response = new BaseResponse<>();
        response.setResult(bookService.updateBook(request));
        response.setCode(ErrorCode.BOOK_UPDATED.getCode());
        response.setMessage(ErrorCode.BOOK_UPDATED.getMessage());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletebook/{id}")
    ResponseEntity<BaseResponse<ErrorCode>> deleteBook(@PathVariable long id) {
        ErrorCode result = bookService.deleteBookById(id);
        BaseResponse<ErrorCode> response = new BaseResponse<>();
        response.setCode(result.getCode());
        response.setMessage(result.getMessage());
        return ResponseEntity.ok(response);
    }
}
