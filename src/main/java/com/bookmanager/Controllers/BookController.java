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
import com.bookmanager.DTOs.Response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/post")
    @Tag(name = "Book Management", description = "API quản lý sách trong thư viện")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Tạo sách mới",
            description = "Thêm một hoặc nhiều bản sao của một đầu sách vào thư viện"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo sách thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    ResponseEntity<BaseResponse<List<Book>>> Bookcreate(@RequestBody CreateBookRequest request) {
        BaseResponse <List<Book>> response = new BaseResponse<>();
        response.setResult(bookService.Bookcreate(request));
        response.setCode(ErrorCode.BOOK_CREATED.getCode());
        response.setMessage(ErrorCode.BOOK_CREATED.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getallbooks")
    @Operation(
            summary = "Lấy danh sách tất cả sách",
            description = "Trả về danh sách tất cả các sách trong thư viện"
    )
    ResponseEntity<BaseResponse<List<GetAllBookResponse>>> getAllBook() {
        BaseResponse<List<GetAllBookResponse>> response = new BaseResponse<>();
        response.setResult(bookService.GetAllBooks());
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getbookbyid/{id}")
    @Operation(
            summary = "Lấy thông tin sách theo ID",
            description = "Trả về thông tin chi tiết của một cuốn sách dựa trên ID"
    )
    ResponseEntity<BaseResponse<GetBookByIdResponse>> getBookById(@PathVariable long id) {
        BaseResponse<GetBookByIdResponse> response = new BaseResponse<>();
        response.setResult(bookService.findById(id));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getbookbytitle/{title}")
    @Operation(
            summary = "Tìm sách theo tên",
            description = "Tìm kiếm sách theo tên (hỗ trợ tìm kiếm gần đúng)"
    )
    ResponseEntity<BaseResponse<List<GetBookByTitleResponse>>> findByBookTitle_Title(@PathVariable String title) {
        BaseResponse<List<GetBookByTitleResponse>> response = new BaseResponse<>();
        response.setResult(bookService.findByBookTitle_Title(title));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatebook")
    @Operation(
            summary = "Cập nhật thông tin sách",
            description = "Cập nhật trạng thái hoặc thông tin của sách"
    )
     ResponseEntity<BaseResponse<BookUpdateResponse>> updateBook(@RequestBody UpdateBookRequest request) {
        BaseResponse<BookUpdateResponse> response = new BaseResponse<>();
        response.setResult(bookService.updateBook(request));
        response.setCode(ErrorCode.BOOK_UPDATED.getCode());
        response.setMessage(ErrorCode.BOOK_UPDATED.getMessage());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletebook/{id}")
    @Operation(
            summary = "Xóa sách",
            description = "Xóa một cuốn sách khỏi hệ thống"
    )
    ResponseEntity<BaseResponse<ErrorCode>> deleteBook(@PathVariable long id) {
        ErrorCode result = bookService.deleteBookById(id);
        BaseResponse<ErrorCode> response = new BaseResponse<>();
        response.setCode(result.getCode());
        response.setMessage(result.getMessage());
        return ResponseEntity.ok(response);
    }
}
