package org.javacore.DAO;

import org.javacore.Domain.Customer;
import org.javacore.Domain.Transaction;

import java.util.List;

public interface TransactionService extends CRUD<Transaction>{

    List<Transaction> findByAccountId(Integer accountId);
}
