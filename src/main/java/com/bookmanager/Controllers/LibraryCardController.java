package com.bookmanager.Controllers;

import com.bookmanager.DTOs.Request.Book.BorrowBookRequest;
import com.bookmanager.DTOs.Request.Book.CompensationRequest;
import com.bookmanager.DTOs.Request.Book.ReturnBookRequest;
import com.bookmanager.DTOs.Request.Book.UserReturnBookRequest;
import com.bookmanager.DTOs.Request.UpdateBorrowCardRequest;
import com.bookmanager.DTOs.Response.*;
import com.bookmanager.DTOs.Response.Book.BorrowBookResponse;
import com.bookmanager.DTOs.Response.Book.ReturnBookResponse;
import com.bookmanager.DTOs.Response.Book.UserReturnBookResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Models.LibraryCard;
import com.bookmanager.Repositories.LibraryCardRepository;
import com.bookmanager.Services.LibraryCardService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/library")
@Tag(name = "Library Card Management", description = "API quản lý mượn/trả sách trong thư viện")
@SecurityRequirement(name = "bearerAuth")
public class LibraryCardController {
    @Autowired
    private LibraryCardRepository libraryCardRepository;

    @Autowired
    private LibraryCardService libraryCardService;

    @PostMapping("/borrowbook")
    @Operation(
            summary = "Mượn sách",
            description = "Người dùng mượn sách từ thư viện. Chỉ mượn được nếu không có sách chưa trả hoặc đang bị phạt"
    )

    BaseResponse<BorrowBookResponse> borrowBook(@RequestBody BorrowBookRequest request){
        BaseResponse<BorrowBookResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.borrowBook(request));
        response.setCode(ErrorCode.BORROW_BOOK_SUCCESS.getCode());
        response.setMessage(ErrorCode.BORROW_BOOK_SUCCESS.getMessage());
        return response;
    }

    @GetMapping("/listborrowcard")
    @Operation(
            summary = "Lấy danh sách tất cả phiếu mượn",
            description = "Xem danh sách tất cả các phiếu mượn sách trong hệ thống (Dành cho Admin/Librarian)"
    )

    ResponseEntity<BaseResponse<List<ListBorrowCardResponse>>> getListBorrowCardResponse(){
        BaseResponse<List<ListBorrowCardResponse>> response = new BaseResponse<>();
        response.setResult(libraryCardService.getListBorrowCardResponse());
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateborrowcard")
    @Operation(
            summary = "Cập nhật trạng thái phiếu mượn",
            description = "Cập nhật trạng thái của phiếu mượn sách"
    )

    ResponseEntity<BaseResponse<UpdateBorrowCardResponse>> updateBorrowCard(@RequestBody UpdateBorrowCardRequest request){
        BaseResponse<UpdateBorrowCardResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.updateBorrowCard(request));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteborrowcard/{id}")
    @Operation(
            summary = "Xóa phiếu mượn",
            description = "Xóa một phiếu mượn sách khỏi hệ thống"
    )

    ResponseEntity<BaseResponse<LibraryCard>> deleteBorrowCard(@Parameter(description = "ID của phiếu mượn cần xóa", required = true) @PathVariable Long id) {
        ErrorCode result = libraryCardService.deleteBorrowCard(id);
        BaseResponse<LibraryCard> response = new BaseResponse<>();
        response.setCode(result.getCode());
        response.setMessage(result.getMessage());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/userreturnbook")
    @Operation(
            summary = "Người dùng yêu cầu trả sách",
            description = "Người dùng gửi yêu cầu trả sách với ghi chú về tình trạng sách"
    )

    ResponseEntity<BaseResponse<UserReturnBookResponse>> getUserReturnBookResponse(@RequestBody UserReturnBookRequest request){
        BaseResponse<UserReturnBookResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.userReturnBook(request));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/returnbook")
    @Operation(
            summary = "Xử lý trả sách (Librarian)",
            description = "Thủ thư xác nhận trả sách và xử lý các trường hợp: trả đúng hạn, trễ hạn, hư hỏng, mất sách"
    )

    BaseResponse<ReturnBookResponse> returnBook(@RequestBody ReturnBookRequest request){
        BaseResponse<ReturnBookResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.returnBook(request));
        return response;
    }

    @PostMapping("/compensation")
    @Operation(
            summary = "Thanh toán bồi thường",
            description = "Người dùng thanh toán tiền bồi thường cho sách hư hỏng hoặc mất"
    )

    BaseResponse<CompensationResponse> payCompensation(@RequestBody CompensationRequest request){
        BaseResponse<CompensationResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.payCompensation(request));
        response.setCode(ErrorCode.COMPENSATION.getCode());
        response.setMessage(ErrorCode.COMPENSATION.getMessage());
        return response;
    }

    @GetMapping("/my-borrows")
    @Operation(
            summary = "Xem lịch sử mượn sách của tôi",
            description = "Người dùng xem lịch sử mượn sách của chính mình"
    )

    public ResponseEntity<BaseResponse<List<UserBorrowHistoryResponse>>> getMyBorrowHistory(HttpServletRequest request) throws ParseException, JOSEException {
        String token = request.getHeader("Authorization").substring(7);
        SignedJWT signedJWT = SignedJWT.parse(token);
        String email = signedJWT.getJWTClaimsSet().getStringClaim("email");

        BaseResponse<List<UserBorrowHistoryResponse>> response = new BaseResponse<>();
        response.setResult(libraryCardService.getBorrowHistoryByEmail(email));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }
}
