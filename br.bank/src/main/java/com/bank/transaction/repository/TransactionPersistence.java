package com.bank.transaction.repository;

import com.bank.transaction.Transaction;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionPersistence extends ListCrudRepository<Transaction, Long> {

}
