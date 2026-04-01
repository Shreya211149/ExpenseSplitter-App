package com.example.splitkro.service;

import com.example.splitkro.dto.request.UserRequest;
import com.example.splitkro.dto.response.UserResponse;
import com.example.splitkro.exception.CustomerAlreadyExistException;
import com.example.splitkro.exception.InvalidCredentialsException;
import com.example.splitkro.exception.UserNotFoundException;
import com.example.splitkro.model.User;
import com.example.splitkro.repository.UserRepository;
import com.example.splitkro.security.JwtUtil;
import com.example.splitkro.transformer.UserTransformer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserResponse registerUser(@Valid UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
              throw new CustomerAlreadyExistException("Email already registered");
          }
        User user= UserTransformer.UserRequestToUser(request);

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser=userRepository.save(user);
        UserResponse userResponse=UserTransformer.userToUserResponse(savedUser);
        return userResponse;
    }
    public String loginUser(UserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserTransformer.userToUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserTransformer::userToUserResponse)
                .collect(Collectors.toList());
    }
}
