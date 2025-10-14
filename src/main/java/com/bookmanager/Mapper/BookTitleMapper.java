package com.bookmanager.Mapper;

import com.bookmanager.DTOs.Request.BookTitle.BookTitleUpdateRequest;
import com.bookmanager.DTOs.Response.UpdateBookTitleResponse;
import com.bookmanager.Models.BookTitle;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookTitleMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void UpdateBookTitle(BookTitleUpdateRequest request, @MappingTarget BookTitle bookTitle);

    UpdateBookTitleResponse toUpdateBookTitleResponse(BookTitle bookTitle);
}
