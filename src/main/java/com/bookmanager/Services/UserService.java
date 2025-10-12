package com.bookmanager.Services;

import com.bookmanager.DTOs.Request.UserCreationRequest;
import com.bookmanager.DTOs.Request.UserUpdateRequest;
import com.bookmanager.DTOs.Response.*;
import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Exception.RException;
import com.bookmanager.Helpers.TextTranf;
import com.bookmanager.Mapper.UserMapper;
import com.bookmanager.Models.User;
import com.bookmanager.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createRequest(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RException(ErrorCode.USERNAME_EXISTED);
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public GetUserResponse getUserById(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RException(ErrorCode.USER_NOT_FOUND));
        GetUserResponse response = userMapper.toGetUserResponse(user);

        List<BorrowCardSumaryResponse> cards = user.getBorrowCards().stream()
                .map(card -> new BorrowCardSumaryResponse(
                        card.getBook().getBookTitle().getTitle(),
                        card.getBorrowDate(),
                        card.getDueDate(),
                        card.getReturnDate(),
                        card.getStatus()
                ))
                .toList();
        response.setBorrowCard(cards);
        return response;
    }

    public List<GetUserResponse> getUserByName(String name){
        String normalizedInput = TextTranf.removeVietnameseAccents(name.toLowerCase());
        List<User> users = userRepository.findAll();
        List<User> matchedUsers = users.stream()
                .filter(user -> {
                    String nameNormalized = TextTranf.removeVietnameseAccents(user.getName().toLowerCase());
                    return nameNormalized.contains(normalizedInput);
                })
                .toList();
        if(matchedUsers.isEmpty())
            throw new RException(ErrorCode.USER_NOT_FOUND);
        return userMapper.toGetUserResponses(matchedUsers);
    }

    public List<GetUserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();
        return userMapper.toGetUserResponses(users);
    }

    /*public List<User> seachUserByKeyWord( String keyword) {
        List<User> result = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(user);
            }
            if (user.getGender().toLowerCase().contains(keyword.toLowerCase())){
                result.add(user);
            }
            if (user.getRole().toLowerCase().contains(keyword.toLowerCase())){
                result.add(user);
            }
        }
        return result;
    }
*/
    public ErrorCode deleteUserById(long id){
        User user = userRepository.findById(id).
                orElseThrow(() -> new RException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
        return ErrorCode.USER_DELETED;
    }

    public UpdateUserResponse updateUser(UserUpdateRequest request){
        User userUpdate = userRepository.findById(request.getId())
                .orElseThrow(() -> new RException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(request, userUpdate);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String hashed = passwordEncoder.encode(request.getPassword());
            userUpdate.setPassword(hashed);
        }
        userRepository.save(userUpdate);
        return userMapper.toUpdateUserResponse(userUpdate);
    }

    public UserInfoResponse getUserInfoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RException(ErrorCode.USER_NOT_FOUND));

        return UserInfoResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .gender(user.getGender())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .dob(user.getDob())
                        .address(user.getAddress())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .status(user.getStatus())
                        .build();
    }

}

