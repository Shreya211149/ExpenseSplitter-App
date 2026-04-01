package com.example.splitkro.dto.request;

import com.example.splitkro.Enum.SplitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ExpenseRequest {

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Payer is required")
    private Long payerId;

    @NotNull(message = "Group is required")
    private Long groupId;

    @NotNull(message = "Split type is required")
    private SplitType splitType;

    private List<SplitDetail> splitDetails;

    @Data
    public static class SplitDetail {
        private Long userId;
        private BigDecimal value;
    }
}
