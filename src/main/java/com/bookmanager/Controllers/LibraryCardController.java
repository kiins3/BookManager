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
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryCardController {
    @Autowired
    private LibraryCardRepository libraryCardRepository;

    @Autowired
    private LibraryCardService libraryCardService;

    @PostMapping("/borrowbook")
    BaseResponse<BorrowBookResponse> borrowBook(@RequestBody BorrowBookRequest request){
        BaseResponse<BorrowBookResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.borrowBook(request));
        response.setCode(ErrorCode.BORROW_BOOK_SUCCESS.getCode());
        response.setMessage(ErrorCode.BORROW_BOOK_SUCCESS.getMessage());
        return response;
    }

    @GetMapping("/listborrowcard")
    ResponseEntity<BaseResponse<List<ListBorrowCardResponse>>> getListBorrowCardResponse(){
        BaseResponse<List<ListBorrowCardResponse>> response = new BaseResponse<>();
        response.setResult(libraryCardService.getListBorrowCardResponse());
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateborrowcard")
    ResponseEntity<BaseResponse<UpdateBorrowCardResponse>> updateBorrowCard(@RequestBody UpdateBorrowCardRequest request){
        BaseResponse<UpdateBorrowCardResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.updateBorrowCard(request));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteborrowcard/{id}")
    ResponseEntity<BaseResponse<LibraryCard>> deleteBorrowCard(@PathVariable Long id) {
        ErrorCode result = libraryCardService.deleteBorrowCard(id);
        BaseResponse<LibraryCard> response = new BaseResponse<>();
        response.setCode(result.getCode());
        response.setMessage(result.getMessage());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/userreturnbook")
    ResponseEntity<BaseResponse<UserReturnBookResponse>> getUserReturnBookResponse(@RequestBody UserReturnBookRequest request){
        BaseResponse<UserReturnBookResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.userReturnBook(request));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/returnbook")
    BaseResponse<ReturnBookResponse> returnBook(@RequestBody ReturnBookRequest request){
        BaseResponse<ReturnBookResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.returnBook(request));
        return response;
    }

    @PostMapping("/compensation")
    BaseResponse<CompensationResponse> payCompensation(@RequestBody CompensationRequest request){
        BaseResponse<CompensationResponse> response = new BaseResponse<>();
        response.setResult(libraryCardService.payCompensation(request));
        response.setCode(ErrorCode.COMPENSATION.getCode());
        response.setMessage(ErrorCode.COMPENSATION.getMessage());
        return response;
    }

    @GetMapping("/my-borrows")
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
