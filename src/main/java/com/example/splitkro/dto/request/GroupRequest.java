package com.example.splitkro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GroupRequest {
    @NotBlank(message = "Group name is required")
    private String name;

    private Long creatorId;

    @NotEmpty(message = "At least one member is required")
    private List<Long> memberIds;
}
