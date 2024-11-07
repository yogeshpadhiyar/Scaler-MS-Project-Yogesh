package com.yogesh.scalermsprojectyogesh.transaction.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yogesh.scalermsprojectyogesh.service.RequestMapper;
import com.yogesh.scalermsprojectyogesh.transaction.model.entity.Transaction;
import com.yogesh.scalermsprojectyogesh.transaction.model.entity.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionBean implements RequestMapper<Transaction> {
    private Long transactionId;
    private Long userId;
    private Long categoryId;
    private BigDecimal openingAmount;
    private BigDecimal transactionAmount;
    private BigDecimal closingAmount;
    private Long approvedUserId;
    private String description;
    private int transactionType;

    @Override
    public Transaction createEntityBean() {
        return Transaction.builder()
                .userId(userId)
                .categoryId(categoryId)
                .transactionAmount(transactionAmount)
                .approvedUserId(approvedUserId)
                .description(description)
                .transactionType(TransactionType.valueOf(transactionType))
                .build();
    }
}
