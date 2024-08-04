package com.bank.wallet;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Albert Cabral
 * @param id
 * @param fullName
 * @param cpf
 * @param email
 * @param password
 * @param type
 * @param balance
 */
@Table
public record Wallet(
        @Id Long id, String fullName, Long cpf, String email,
        String password, int type, BigDecimal balance, String countId) {

    public Wallet credit(BigDecimal value) {
        return new Wallet(
                id, fullName, cpf, email, password, type,
                balance.add(value), countId);
    }

    public Wallet debit(BigDecimal value) {
        return new Wallet(
                id, fullName, cpf, email, password, type,
                balance.subtract(value), countId);
    }

    public <T> T load(String name, T contract) {
        Map<String, T> map = new HashMap<>();

        return map.put(name, contract);
    }

    public <T> T investment(String name, T investiment) {
        Map<String, T> map = new HashMap<>();

        return map.put(name, investiment);
    }

}
