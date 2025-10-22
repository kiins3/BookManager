package com.bookmanager.Controllers;

import com.bookmanager.DTOs.Request.FilterBookTitleRequest;
import com.bookmanager.DTOs.Response.BaseResponse;
import com.bookmanager.DTOs.Response.FilterBookTitleResponse;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Models.BookTitle;
import com.bookmanager.Repositories.CategoryReposirory;
import com.bookmanager.Services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Tag(name = "Filter Book", description = "API lọc sách theo thể loại trong thư viện")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    @Autowired
    CategoryReposirory categoryReposirory;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/filter")
    BaseResponse<List<FilterBookTitleResponse>> filterBookTitle(@RequestBody FilterBookTitleRequest request) {
        BaseResponse<List<FilterBookTitleResponse>> response = new BaseResponse<>();
        response.setResult(categoryService.filterBooksByClassify(request.getClassifyMap()));
        response.setMessage(ErrorCode.SUCCESS.getMessage());
        response.setCode(ErrorCode.SUCCESS.getCode());
        return response;
    }
}
