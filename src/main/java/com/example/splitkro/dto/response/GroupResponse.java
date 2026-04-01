package com.example.splitkro.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class GroupResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private String creatorName;
    private List<String> memberNames;
}
