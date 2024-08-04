package com.bank.transaction.service;

import com.bank.authorization.service.AuthorizationService;
import com.bank.notification.service.NotificationService;
import com.bank.transaction.constants.TransactionType;
import com.bank.transaction.exception.InvalidTransactionException;
import com.bank.transaction.repository.TransactionPersistence;
import com.bank.transaction.Transaction;
import com.bank.wallet.persistence.WalletRepository;
import com.bank.wallet.Wallet;
import com.bank.wallet.constants.WalletType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    public TransactionService(
            AuthorizationService authorizationService,
            NotificationService notificationService,
            TransactionPersistence transactionPersistence,
            WalletRepository walletRepository) {

        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.transactionPersistence = transactionPersistence;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transaction create(
            Transaction transaction) throws InvalidTransactionException {
        // validating transaction
        _validate(transaction);

        // create a new transaction
        var newTransaction = transactionPersistence.save(transaction);

        // find wallets of payer and, payee
        var walletPayer = walletRepository.findById(transaction.payer()).get();
        var walletPayee = walletRepository.findById(transaction.payee()).get();

        // debit new balance from payer and credit to payee balance
        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));

        // authorize transaction
        authorizationService.authorize(transaction);

        // send transaction notification
        notificationService.notify(transaction);

        return newTransaction;
    }

    public List<Transaction> findAll() {
        return transactionPersistence.findAll();
    }

    /**
     * Rules:
     *  - the payer has enough balance
     *  - tha payer is not the payee
     * @param transaction a new transaction
     */
    private void _validate(Transaction transaction) throws InvalidTransactionException {
        walletRepository.findById(transaction.payer())
                .map(payer ->
                        walletRepository.findById(transaction.payer())).orElseThrow(
                                () -> new InvalidTransactionException(
                                        "Payer wasn't found - %s".formatted(transaction)))
                .map(payer ->
                        _isTransactionValid(transaction, payer) ? transaction : null).orElseThrow(
                                () -> new InvalidTransactionException(
                                        "Invalid transaction - %s".formatted(transaction)));
    }

    private boolean _isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMMON ||
                payer.type() == WalletType.COMPANY ||
                payer.type() == WalletType.GOLD &&
                payer.balance().compareTo(transaction.value()) >= 0 &&
                !payer.id().equals(transaction.payee()) &&
                Arrays.stream(TransactionType.getAllTransactionTypes())
                        .toList().contains(transaction.type());
    }

    private final AuthorizationService authorizationService;

    private final NotificationService notificationService;

    private final TransactionPersistence transactionPersistence;

    private final WalletRepository walletRepository;

}
