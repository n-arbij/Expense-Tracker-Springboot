package com.fintech.expense_planner.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.fintech.expense_planner.dto.TransactionDto;
import com.fintech.expense_planner.model.Transaction;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    public TransactionDto.Response toResponse(Transaction transaction){
        return new TransactionDto.Response(
            transaction.getId(),
            transaction.getAmount(),
            transaction.getDescription(),
            transaction.getType(),
            transaction.getCategory().getId(),
            transaction.getCategory().getName(),
            transaction.getGoal().getId(),
            transaction.getGoal().getName(),
            transaction.getDate()
        );
    }

    public List<TransactionDto.Response> toResponseList(List<Transaction> transactions){
        return transactions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
