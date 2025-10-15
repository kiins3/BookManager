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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Management", description = "API quản lý người dùng (Dành cho Admin)")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createuser")
    @Operation(
            summary = "Tạo người dùng mới",
            description = "Admin tạo tài khoản người dùng mới trong hệ thống"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tạo người dùng thành công"),
            @ApiResponse(responseCode = "400", description = "Username hoặc Email đã tồn tại"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    ResponseEntity<BaseResponse<User>> createUser(@RequestBody UserCreationRequest request) {
        BaseResponse<User> response = new BaseResponse<>();
        response.setCode(ErrorCode.USER_CREATED.getCode());
        response.setMessage(ErrorCode.USER_CREATED.getMessage());
        response.setResult(userService.createRequest(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getuserbyid/{id}")
    @Operation(
            summary = "Lấy thông tin người dùng theo ID",
            description = "Xem thông tin chi tiết của một người dùng cụ thể"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    ResponseEntity<BaseResponse<GetUserResponse>> getUserById(@Parameter(description = "ID của người dùng", required = true) @PathVariable long id) {
        BaseResponse<GetUserResponse> response = new BaseResponse<>();
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        response.setResult(userService.getUserById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getuserbyname/{name}")
    @Operation(
            summary = "Tìm người dùng theo tên",
            description = "Tìm kiếm người dùng theo tên (hỗ trợ tìm kiếm gần đúng và bỏ dấu tiếng Việt)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tìm kiếm thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    ResponseEntity<BaseResponse<List<GetUserResponse>>> getUserByName(@Parameter(description = "Tên người dùng cần tìm", required = true) @PathVariable String name) {
        BaseResponse<List<GetUserResponse>> response = new BaseResponse<>();
        response.setCode(ErrorCode.SUCCESS.getCode());
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        response.setResult(userService.getUserByName(name));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getallusers")
    @Operation(
            summary = "Lấy danh sách tất cả người dùng",
            description = "Xem danh sách tất cả người dùng trong hệ thống"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
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
    @Operation(
            summary = "Xóa người dùng",
            description = "Xóa một người dùng khỏi hệ thống"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa người dùng thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    ResponseEntity<BaseResponse<User>> deleteUserById(@Parameter(description = "ID của người dùng cần xóa", required = true) @PathVariable long id) {
        ErrorCode result = userService.deleteUserById(id);
        BaseResponse<User> response = new BaseResponse<>();
            response.setCode(result.getCode());
            response.setMessage(result.getMessage());
            return ResponseEntity.ok(response);
    }

    @PatchMapping("/updateuser")
    @Operation(
            summary = "Cập nhật thông tin người dùng",
            description = "Admin cập nhật thông tin của người dùng (có thể cập nhật một phần)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "403", description = "Không có quyền truy cập")
    })
    public ResponseEntity<BaseResponse<UpdateUserResponse>> updateUser(@RequestBody UserUpdateRequest request) {
        BaseResponse<UpdateUserResponse> response = new BaseResponse<>();
        response.setCode(ErrorCode.USER_UPDATED.getCode());
        response.setMessage(ErrorCode.USER_UPDATED.getMessage());
        response.setResult(userService.updateUser(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myinfo")
    @Operation(
            summary = "Xem thông tin cá nhân",
            description = "Người dùng xem thông tin tài khoản của chính mình"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công"),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
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
