package com.bank.transaction.controller;

import com.bank.transaction.Transaction;
import com.bank.transaction.exception.InvalidTransactionException;
import com.bank.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(
            @RequestBody Transaction transaction) throws InvalidTransactionException {

        return transactionService.create(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.findAll();
    }

    private final TransactionService transactionService;

}
