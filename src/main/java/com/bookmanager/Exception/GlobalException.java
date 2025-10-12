package com.bookmanager.Exception;


import com.bookmanager.DTOs.Response.BaseResponse;
import com.bookmanager.DTOs.Response.GetUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException e) {
        BaseResponse response = new BaseResponse();

        response.setMessage(e.getMessage());
        response.setCode(1001);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RException.class)
    public ResponseEntity<BaseResponse> handleException(RException e) {
        ErrorCode errorCode = e.getErrorCode();
        BaseResponse response = new BaseResponse();

        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
