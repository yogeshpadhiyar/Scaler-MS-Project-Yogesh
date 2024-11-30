package com.yogesh.scalermsprojectyogesh.transaction.model.entity;

import com.yogesh.scalermsprojectyogesh.model.BaseModel;
import com.yogesh.scalermsprojectyogesh.service.ResponseMapper;
import com.yogesh.scalermsprojectyogesh.transaction.model.TransactionBean;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_master")
public class Transaction extends BaseModel implements ResponseMapper<TransactionBean> {

    private Long userId;
    private Long categoryId;
    private BigDecimal openingAmount;
    private BigDecimal transactionAmount;
    private BigDecimal closingAmount;
    private Long approvedUserId;
    private String description;
    private TransactionType transactionType;

    @Override
    public TransactionBean createResponseBean() {
        return TransactionBean.builder()
                .transactionId(getId())
                .userId(userId)
                .categoryId(categoryId)
                .openingAmount(openingAmount)
                .transactionAmount(transactionAmount)
                .closingAmount(closingAmount)
                .approvedUserId(approvedUserId)
                .description(description)
                .build();
    }
}
