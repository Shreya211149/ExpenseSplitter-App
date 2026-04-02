package com.example.splitkro.service;

import com.example.splitkro.dto.request.ExpenseRequest;
import com.example.splitkro.dto.response.DebtResponse;
import com.example.splitkro.dto.response.ExpenseResponse;
import com.example.splitkro.exception.GroupNotFoundException;
import com.example.splitkro.exception.PayerNotFoundException;
import com.example.splitkro.model.Expense;
import com.example.splitkro.model.ExpenseShare;
import com.example.splitkro.model.Group;
import com.example.splitkro.model.User;
import com.example.splitkro.repository.ExpenseRepository;
import com.example.splitkro.repository.ExpenseShareRepository;
import com.example.splitkro.repository.GroupRepository;
import com.example.splitkro.repository.UserRepository;
import com.example.splitkro.transformer.ExpenseTransformer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;      // ← add final
    private final ExpenseShareRepository expenseShareRepository; // ← add final
    private final UserRepository userRepository;            // ← add final
    private final GroupRepository groupRepository;

    @Transactional
    public ExpenseResponse createExpense(@Valid ExpenseRequest request) {
        User payer = userRepository.findById(request.getPayerId())
                .orElseThrow(() -> new PayerNotFoundException("Payer not found"));
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException("Group not found"));

        Expense expense= ExpenseTransformer.expenseReqToExpense(request,payer,group);
        Expense saved = expenseRepository.save(expense);

        List<ExpenseShare> shares = calculateShares(saved, group, request);
        expenseShareRepository.saveAll(shares);
        return ExpenseTransformer.expenseToExpenseResponse(saved,shares);
    }
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByGroup(Long groupId) {
        List<Expense> expenses = expenseRepository.findByGroupId(groupId);
        return expenses.stream().map(expense -> {
            List<ExpenseShare> shares = expenseShareRepository.findByExpenseId(expense.getId());
            return ExpenseTransformer.expenseToExpenseResponse(expense, shares);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DebtResponse> calculateDebts(Long groupId) {
        List<ExpenseShare> allShares = expenseShareRepository.findByExpenseGroupId(groupId);
        List<Expense> allExpenses = expenseRepository.findByGroupId(groupId);

        Map<Long, Map<Long, BigDecimal>> owes = new HashMap<>();

        for (ExpenseShare share : allShares) {
            Long debtorId  = share.getUser().getId();
            Long creditorId = share.getExpense().getPayer().getId();

            if (debtorId.equals(creditorId)) continue;

            owes.computeIfAbsent(debtorId, k -> new HashMap<>())
                    .merge(creditorId, share.getAmount(), BigDecimal::add);
        }
        List<DebtResponse> debts = new ArrayList<>();

        for (Map.Entry<Long, Map<Long, BigDecimal>> debtorEntry : owes.entrySet()) {
            Long debtorId = debtorEntry.getKey();
            for (Map.Entry<Long, BigDecimal> creditorEntry : debtorEntry.getValue().entrySet()) {
                Long creditorId = creditorEntry.getKey();
                BigDecimal amount = creditorEntry.getValue();

                BigDecimal reverseAmount = owes
                        .getOrDefault(creditorId, new HashMap<>())
                        .getOrDefault(debtorId, BigDecimal.ZERO);

                BigDecimal netAmount = amount.subtract(reverseAmount);

                if (netAmount.compareTo(BigDecimal.ZERO) > 0) {
                    User debtor   = userRepository.findById(debtorId).orElseThrow();
                    User creditor = userRepository.findById(creditorId).orElseThrow();

                    DebtResponse debt = new DebtResponse();
                    debt.setFromUserName(debtor.getName());
                    debt.setToUserName(creditor.getName());
                    debt.setAmount(netAmount.setScale(2, RoundingMode.HALF_UP));
                    debts.add(debt);
                }
            }
        }
        return debts;

    }
    private List<ExpenseShare> calculateShares(Expense expense, Group group, ExpenseRequest request) {
        List<User> members = group.getMembers();
        List<ExpenseShare> shares = new ArrayList<>();

        switch (request.getSplitType()) {

            case EQUAL -> {
                BigDecimal share = expense.getAmount()
                        .divide(BigDecimal.valueOf(members.size()), 2, RoundingMode.HALF_UP);
                for (User member : members) {
                    shares.add(buildShare(expense, member, share));
                }
            }

            case EXACT -> {
                BigDecimal total = request.getSplitDetails().stream()
                        .map(ExpenseRequest.SplitDetail::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (total.compareTo(expense.getAmount()) != 0) {
                    throw new RuntimeException("Exact amounts must sum to total expense amount");
                }
                for (ExpenseRequest.SplitDetail detail : request.getSplitDetails()) {
                    User user = userRepository.findById(detail.getUserId()).orElseThrow();
                    shares.add(buildShare(expense, user, detail.getValue()));
                }
            }

            case PERCENT -> {
                BigDecimal totalPercent = request.getSplitDetails().stream()
                        .map(ExpenseRequest.SplitDetail::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (totalPercent.compareTo(BigDecimal.valueOf(100)) != 0) {
                    throw new RuntimeException("Percentages must add up to 100");
                }
                for (ExpenseRequest.SplitDetail detail : request.getSplitDetails()) {
                    User user = userRepository.findById(detail.getUserId()).orElseThrow();
                    BigDecimal shareAmount = expense.getAmount()
                            .multiply(detail.getValue())
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    shares.add(buildShare(expense, user, shareAmount));
                }
            }
        }

        return shares;
    }
    private ExpenseShare buildShare(Expense expense, User user, BigDecimal amount) {
        ExpenseShare share = new ExpenseShare();
        share.setExpense(expense);
        share.setUser(user);
        share.setAmount(amount);
        return share;
    }

}
