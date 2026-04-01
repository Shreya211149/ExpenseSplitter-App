package com.example.splitkro.controller;

import com.example.splitkro.dto.response.DebtResponse;
import com.example.splitkro.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/debts")
public class DebtController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<DebtResponse>> getDebts(@RequestParam Long groupId) {
        List<DebtResponse> response = expenseService.calculateDebts(groupId);
        return ResponseEntity.ok(response);
    }
}
