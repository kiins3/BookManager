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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ResponseEntity<BaseResponse<AuthenticationResponse>> Authenticate(@RequestBody AuthenticationRequest request){
        BaseResponse<AuthenticationResponse> response = new BaseResponse<>();
        response.setCode(ErrorCode.LOGIN_SUCCESS.getCode());
        response.setMessage(ErrorCode.LOGIN_SUCCESS.getMessage());
        response.setResult(authenticationService.authenticate(request));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/introspect")
    ResponseEntity<BaseResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        BaseResponse<IntrospectResponse> response = new BaseResponse<>();
        response.setResult(authenticationService.introspect(request));
        response.setResult(authenticationService.introspect(request));
        return ResponseEntity.ok(response);
    }

}
