package com.bank.transaction;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Albert Cabral
 * @param id
 * @param payer
 * @param payee
 * @param type
 * @param value
 * @param createdAt
 */
@Table
public record Transaction(
        @Id Long id, Long payer, Long payee, String type,
        BigDecimal value, @CreatedDate LocalDateTime createdAt) {

    public Transaction {
        value = value.setScale(_NEW_SCALE);
    }

    private static final int _NEW_SCALE = 2;

}
