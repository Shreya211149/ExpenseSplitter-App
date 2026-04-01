package com.example.splitkro.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String email;
    private LocalDateTime joinedAt;
}
