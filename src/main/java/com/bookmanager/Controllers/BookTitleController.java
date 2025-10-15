package com.bookmanager.Controllers;

import com.bookmanager.DTOs.Request.BookTitle.BookTitleCreationRequest;
import com.bookmanager.DTOs.Request.BookTitle.BookTitleUpdateRequest;
import com.bookmanager.DTOs.Response.BaseResponse;
import com.bookmanager.DTOs.Response.UpdateBookTitleResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Models.BookTitle;
import com.bookmanager.Services.BookTitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booktitles")
@Tag(name = "Book Title Management", description = "API quản lý thông tin đầu sách (Book Titles)")
@SecurityRequirement(name = "bearerAuth")
public class BookTitleController {
    @Autowired
    BookTitleService bookTitleService;

    @GetMapping("/getbooktitlebyid/{id}")
    @Operation(
            summary = "Lấy thông tin đầu sách theo ID",
            description = "Trả về thông tin chi tiết của một đầu sách dựa trên ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đầu sách"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực")
    })
    BaseResponse<Optional<BookTitle>> getBookTitleById( @Parameter(description = "ID của đầu sách", required = true) @PathVariable Long id) {
        BaseResponse<Optional<BookTitle>> response = new BaseResponse<>();
        response.setResult(bookTitleService.getBookTitleById(id));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return response;
    }

    @GetMapping("/getallbooktitle")
    @Operation(
            summary = "Lấy danh sách tất cả đầu sách",
            description = "Trả về danh sách tất cả các đầu sách trong hệ thống"
    )
    BaseResponse<List<BookTitle>> getAllBookTitles() {
        BaseResponse<List<BookTitle>> response = new BaseResponse<>();
        response.setResult(bookTitleService.getAllBookTitles());
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return response;
    }

    @GetMapping("/getbooktitlebytitle/{title}")
    @Operation(
            summary = "Tìm đầu sách theo tên",
            description = "Tìm kiếm đầu sách theo tên (hỗ trợ tìm kiếm gần đúng và bỏ dấu tiếng Việt)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tìm kiếm thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đầu sách"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực")
    })
    BaseResponse<List<BookTitle>> getBookTitleByTitle(@Parameter(description = "Tên đầu sách cần tìm", required = true) @PathVariable String title) {
        BaseResponse<List<BookTitle>> response = new BaseResponse<>();
        response.setResult(bookTitleService.getBookTitleByTitle(title));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return response;
    }

    @PostMapping("/postbooktitle")
    @Operation(
            summary = "Tạo đầu sách mới",
            description = "Thêm một đầu sách mới vào hệ thống cùng với số lượng bản sao"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo đầu sách thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    BaseResponse<BookTitle> createBookTitle(@RequestBody BookTitleCreationRequest request) {
        BaseResponse<BookTitle> response = new BaseResponse<>();
        response.setResult(bookTitleService.createBookTitle(request));
        response.setCode(ErrorCode.BOOKTITLE_CREATED.getCode());
        response.setMessage(ErrorCode.BOOKTITLE_CREATED.getMessage());
        return response;
    }

    @PutMapping("/putbooktitle")
    @Operation(
            summary = "Cập nhật thông tin đầu sách",
            description = "Cập nhật thông tin của một đầu sách đã có"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đầu sách"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    ResponseEntity<BaseResponse<UpdateBookTitleResponse>> updateBookTitle(@RequestBody BookTitleUpdateRequest request) {
        BaseResponse<UpdateBookTitleResponse> response = new BaseResponse<>();
        response.setResult(bookTitleService.updateBookTitle(request));
        response.setCode(ErrorCode.BOOKTITLE_UPDATED.getCode());
        response.setMessage(ErrorCode.BOOKTITLE_UPDATED.getMessage());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletebooktitle/{id}")
    @Operation(
            summary = "Xóa đầu sách",
            description = "Xóa một đầu sách và tất cả các bản sao liên quan khỏi hệ thống"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy đầu sách"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    ResponseEntity<BaseResponse<ErrorCode>> deleteBookTitle(@Parameter(description = "ID của đầu sách cần xóa", required = true) @PathVariable long id) {
        ErrorCode result = bookTitleService.deleteBookTitleById(id);
        BaseResponse<ErrorCode> response = new BaseResponse<>();
        response.setCode(result.getCode());
        response.setMessage(result.getMessage());
        return ResponseEntity.ok(response);
    }
}
