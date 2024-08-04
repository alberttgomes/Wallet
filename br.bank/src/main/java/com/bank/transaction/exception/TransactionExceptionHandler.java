package com.bank.transaction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<Object> handle(
            InvalidTransactionException invalidTransactionException) {

        return ResponseEntity.badRequest().body(invalidTransactionException.getCause());
    }

}
