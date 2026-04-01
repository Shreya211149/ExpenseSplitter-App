package com.example.splitkro.transformer;

import com.example.splitkro.dto.request.UserRequest;
import com.example.splitkro.dto.response.UserResponse;
import com.example.splitkro.model.User;

public class UserTransformer {
    public static User UserRequestToUser(UserRequest userRequest){
      User user=User.builder()
              .name(userRequest.getName())
              .email(userRequest.getEmail())
              .password(userRequest.getPassword())
              .build();
      return  user;
    }

    public static UserResponse userToUserResponse(User savedUser) {
        return UserResponse.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .joinedAt(savedUser.getJoinedAt())
                .build();
    }
}
