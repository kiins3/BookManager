package com.bookmanager.Controllers;

import com.bookmanager.DTOs.Request.AuthenticationRequest;
import com.bookmanager.DTOs.Request.IntrospectRequest;
import com.bookmanager.DTOs.Response.AuthenticationResponse;
import com.bookmanager.DTOs.Response.BaseResponse;
import com.bookmanager.DTOs.Response.IntrospectResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Repositories.UserRepository;
import com.bookmanager.Services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping("/login")
@Tag(name = "Authentication", description = "API xác thực và đăng nhập")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/token")
    @Operation(
            summary = "Đăng nhập",
            description = "Đăng nhập vào hệ thống và nhận JWT token để sử dụng cho các API khác"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công, trả về JWT token"),
            @ApiResponse(responseCode = "400", description = "Sai username hoặc password"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    ResponseEntity<BaseResponse<AuthenticationResponse>> Authenticate(@RequestBody AuthenticationRequest request){
        BaseResponse<AuthenticationResponse> response = new BaseResponse<>();
        response.setCode(ErrorCode.LOGIN_SUCCESS.getCode());
        response.setMessage(ErrorCode.LOGIN_SUCCESS.getMessage());
        response.setResult(authenticationService.authenticate(request));
        return ResponseEntity.ok(response);
    }

/*    @PostMapping("/introspect")
    ResponseEntity<BaseResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        BaseResponse<IntrospectResponse> response = new BaseResponse<>();
        response.setResult(authenticationService.introspect(request));
        response.setResult(authenticationService.introspect(request));
        return ResponseEntity.ok(response);
    }*/

}
