package com.bank.notification.service;

import com.bank.notification.Notification;
import com.bank.notification.exception.NotificationException;
import com.bank.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class NotificationConsumer {

    @Autowired
    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8080:/mock-service/v1")
                .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "bank-br")
    public void receiveNotification(Transaction transaction) {
        var response = restClient.get()
                .retrieve()
                .toEntity(Notification.class);

        if (response.getStatusCode().isError() ||
                Objects.requireNonNull(response.getBody()).message() == null) {

            throw new NotificationException(
                    "Error sending notification %s".formatted(transaction));
        }
    }

    private final RestClient restClient;

}
