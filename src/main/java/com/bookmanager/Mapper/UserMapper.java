package com.bookmanager.Mapper;

import com.bookmanager.DTOs.Request.UserCreationRequest;
import com.bookmanager.DTOs.Request.UserUpdateRequest;
import com.bookmanager.DTOs.Response.GetUserResponse;
import com.bookmanager.DTOs.Response.UpdateUserResponse;
import com.bookmanager.Models.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserUpdateRequest request, @MappingTarget User user);

    UpdateUserResponse toUpdateUserResponse(User user);

    GetUserResponse toGetUserResponse(User user);

    List<GetUserResponse> toGetUserResponses(List<User> users);

}
