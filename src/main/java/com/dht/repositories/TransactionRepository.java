/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repositories;

import com.dht.pojo.Transaction;
import java.util.List;
import java.util.Map;
/**
 *
 * @author phamd
 */
public interface TransactionRepository {
    void addTransaction(Transaction t);
    List<Transaction> getTransactions(Map<String, String> params);
    Transaction getTransactionById(int id);
}
