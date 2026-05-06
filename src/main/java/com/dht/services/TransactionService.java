package com.dht.services;

import com.dht.pojo.Transaction;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    void addTransaction(Map<String, String> params);
    List<Transaction> getTransactions(Map<String, String> params);
}