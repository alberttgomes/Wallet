package com.bank.authorization.service;

import com.bank.authorization.Authorization;
import com.bank.transaction.exception.UnauthorizedTransactionException;
import com.bank.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class AuthorizationService {

    @Autowired
    public AuthorizationService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8080:/mock-service/v1")
                .build();
    }

    public void authorize(Transaction transaction) {
        System.out.println("Authorizing transaction: " + transaction);

        var response =
                restClient.get()
                        .retrieve()
                        .toEntity(Authorization.class);

        if (response.getStatusCode().isError() ||
                !Objects.requireNonNull(
                        response.getBody()).isAuthorized()) {

            throw new UnauthorizedTransactionException(
                    "Unauthorized transaction %s".formatted(transaction));
        }
    }

    private final RestClient restClient;

}