package com.dht.repositories.impl;

import com.dht.pojo.Transaction;
import com.dht.repositories.TransactionRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private LocalSessionFactoryBean factory; // Bắt buộc phải có để lấy Session

    @Override
    public void addTransaction(Transaction t) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(t); // Lưu giao dịch thanh toán vào DB
    }

    @Override
    public Transaction getTransactionById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Transaction.class, id);
    }

    @Override
    public List<Transaction> getTransactions(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Transaction ORDER BY createdAt DESC", Transaction.class);
        return q.getResultList();
    }
}