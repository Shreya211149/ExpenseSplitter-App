package com.example.splitkro.transformer;

import com.example.splitkro.dto.request.ExpenseRequest;
import com.example.splitkro.dto.response.ExpenseResponse;
import com.example.splitkro.model.Expense;
import com.example.splitkro.model.ExpenseShare;
import com.example.splitkro.model.Group;
import com.example.splitkro.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseTransformer {
    public static Expense expenseReqToExpense(ExpenseRequest expenseRequest, User payer, Group group) {
        return Expense.builder()
                .description(expenseRequest.getDescription())
                .amount(expenseRequest.getAmount())
                .payer(payer)
                .group(group)
                .splitType(expenseRequest.getSplitType())
                .build();
    }

    public static ExpenseResponse expenseToExpenseResponse(Expense expense, List<ExpenseShare> shares) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .payerName(expense.getPayer().getName())
                .groupName(expense.getGroup().getName())
                .splitType(expense.getSplitType())
                .shares(shares.stream().map(s -> {
                    ExpenseResponse.ShareDetail detail = new ExpenseResponse.ShareDetail();
                    detail.setUserName(s.getUser().getName());
                    detail.setAmount(s.getAmount());
                    return detail;
                }).collect(Collectors.toList()))
                .build();
    }
}
