package com.example.splitkro.dto.response;

import com.example.splitkro.Enum.SplitType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ExpenseResponse {
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDateTime date;
    private String payerName;
    private String groupName;
    private SplitType splitType;
    private List<ShareDetail> shares;

    @Data
    public static class ShareDetail {
        private String userName;
        private BigDecimal amount;
    }
}
