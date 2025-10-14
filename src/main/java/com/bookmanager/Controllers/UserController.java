package com.bookmanager.Controllers;

import com.bookmanager.DTOs.Request.UserCreationRequest;
import com.bookmanager.DTOs.Request.UserUpdateRequest;
import com.bookmanager.DTOs.Response.*;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Models.User;
import com.bookmanager.Repositories.UserRepository;
import com.bookmanager.Services.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
@Slf4j
@RestController
@RequestMapping ("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createuser")
    ResponseEntity<BaseResponse<User>> createUser(@RequestBody UserCreationRequest request) {
        BaseResponse<User> response = new BaseResponse<>();
        response.setCode(ErrorCode.USER_CREATED.getCode());
        response.setMessage(ErrorCode.USER_CREATED.getMessage());
        response.setResult(userService.createRequest(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getuserbyid/{id}")
    ResponseEntity<BaseResponse<GetUserResponse>> getUserById(@PathVariable long id) {
        BaseResponse<GetUserResponse> response = new BaseResponse<>();
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        response.setResult(userService.getUserById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getuserbyname/{name}")
    ResponseEntity<BaseResponse<List<GetUserResponse>>> getUserByName(@PathVariable String name) {
        BaseResponse<List<GetUserResponse>> response = new BaseResponse<>();
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        response.setResult(userService.getUserByName(name));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getallusers")
    ResponseEntity<BaseResponse<List<GetUserResponse>>> findAll() {
        BaseResponse<List<GetUserResponse>> response = new BaseResponse<>();
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        response.setResult(userService.getAllUsers());
        return ResponseEntity.ok(response);
    }

/*    @GetMapping("/search")
    public List<User> findByKeyword(@RequestParam (required = false, defaultValue = "") String keyword) {
        return userService.seachUserByKeyWord((String) keyword);
    }*/

    @DeleteMapping("/deleteuser/{id}")
    ResponseEntity<BaseResponse<User>> deleteUserById(@PathVariable long id) {
        ErrorCode result = userService.deleteUserById(id);
        BaseResponse<User> response = new BaseResponse<>();
            response.setCode(result.getCode());
            response.setMessage(result.getMessage());
            return ResponseEntity.ok(response);
    }

    @PatchMapping("/updateuser")
    public ResponseEntity<BaseResponse<UpdateUserResponse>> updateUser(@RequestBody UserUpdateRequest request) {
        BaseResponse<UpdateUserResponse> response = new BaseResponse<>();
        response.setCode(ErrorCode.USER_UPDATED.getCode());
        response.setMessage(ErrorCode.USER_UPDATED.getMessage());
        response.setResult(userService.updateUser(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myinfo")
    public ResponseEntity<BaseResponse<UserInfoResponse>> getUserInfo(HttpServletRequest request) throws ParseException, JOSEException {
        String token = request.getHeader("Authorization").substring(7);
        SignedJWT signedJWT = SignedJWT.parse(token);
        String email = signedJWT.getJWTClaimsSet().getStringClaim("email");

        BaseResponse <UserInfoResponse> response = new BaseResponse<>();
        response.setResult(userService.getUserInfo(email));
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }
}
