package com.example.splitkro.dto.response;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class DebtResponse {
    private String fromUserName;
    private String toUserName;
    private BigDecimal amount;
}
