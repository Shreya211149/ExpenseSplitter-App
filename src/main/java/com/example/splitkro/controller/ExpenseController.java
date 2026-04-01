package com.example.splitkro.controller;

import com.example.splitkro.dto.request.ExpenseRequest;
import com.example.splitkro.dto.response.ExpenseResponse;
import com.example.splitkro.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getExpensesByGroup(@RequestParam Long groupId) {
        List<ExpenseResponse> response = expenseService.getExpensesByGroup(groupId);
        return ResponseEntity.ok(response);
    }
}
