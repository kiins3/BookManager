package com.bookmanager.Controllers;

import com.bookmanager.DTOs.Request.BookTitle.BookTitleCreationRequest;
import com.bookmanager.DTOs.Request.BookTitle.BookTitleUpdateRequest;
import com.bookmanager.DTOs.Response.BaseResponse;
import com.bookmanager.DTOs.Response.UpdateBookTitleResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Models.BookTitle;
import com.bookmanager.Services.BookTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booktitles")
public class BookTitleController {
    @Autowired
    BookTitleService bookTitleService;

    @GetMapping("/getbooktitlebyid")
    BaseResponse<Optional<BookTitle>> getBookTitleById(@RequestParam Long id) {
        BaseResponse<Optional<BookTitle>> response = new BaseResponse<>();
        response.setResult(bookTitleService.getBookTitleById(id));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return response;
    }

    @GetMapping("/getallbooktitle")
    BaseResponse<List<BookTitle>> getAllBookTitles() {
        BaseResponse<List<BookTitle>> response = new BaseResponse<>();
        response.setResult(bookTitleService.getAllBookTitles());
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return response;
    }

    @GetMapping("/getbooktitlebytitle")
    BaseResponse<List<BookTitle>> getBookTitleByTitle(@RequestParam String Title) {
        BaseResponse<List<BookTitle>> response = new BaseResponse<>();
        response.setResult(bookTitleService.getBookTitleByTitle(Title));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return response;
    }

    @PostMapping("/postbooktitle")
    BaseResponse<BookTitle> createBookTitle(@RequestBody BookTitleCreationRequest request) {
        BaseResponse<BookTitle> response = new BaseResponse<>();
        response.setResult(bookTitleService.createBookTitle(request));
        response.setCode(ErrorCode.BOOKTITLE_CREATED.getCode());
        response.setMessage(ErrorCode.BOOKTITLE_CREATED.getMessage());
        return response;
    }

    @PutMapping("/putbooktitle/{id}")
    ResponseEntity<BaseResponse<UpdateBookTitleResponse>> updateBookTitle(@RequestBody BookTitleUpdateRequest request) {
        BaseResponse<UpdateBookTitleResponse> response = new BaseResponse<>();
        response.setResult(bookTitleService.updateBookTitle(request));
        response.setCode(ErrorCode.BOOKTITLE_UPDATED.getCode());
        response.setMessage(ErrorCode.BOOKTITLE_UPDATED.getMessage());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletebooktitle")
    ResponseEntity<BaseResponse<BookTitle>> deleteBookTitle(@RequestParam long id) {
        BaseResponse<BookTitle> response = new BaseResponse<>();
        response.setResult(bookTitleService.deleteBookTitleById(id));
        response.setCode(ErrorCode.BOOKTITLE_DELETED.getCode());
        response.setMessage(ErrorCode.BOOKTITLE_DELETED.getMessage());
        return ResponseEntity.ok(response);
    }
}
