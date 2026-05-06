package com.dht.repositories.impl;

import com.dht.pojo.Application;
import com.dht.repositories.ApplicationRepository;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ApplicationRepositoryImpl implements ApplicationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    // 1. Lưu đơn ứng tuyển mới
    @Override
    public Application addApplication(Application app) {
        Session s = this.factory.getObject().getCurrentSession();
        try{
            s.persist(app);
            return app;
        } catch (HibernateException ex){
            ex.printStackTrace();
            return null;
        }
    }

    // 2. Nhà tuyển dụng xem danh sách những người đã nộp vào một công việc cụ thể
    @Override
    public List<Application> getApplicationsByJob(int jobId) {
        Session s = this.factory.getObject().getCurrentSession();
        // Truy vấn dựa trên thuộc tính jobId trong POJO Application
        Query q = s.createQuery("FROM Application a WHERE a.jobId.id = :jobId ORDER BY a.createdAt DESC", Application.class);
        q.setParameter("jobId", jobId);
        
        return q.getResultList();
    }

    // 3. Ứng viên xem lại danh sách các công việc mà mình đã nộp đơn
    @Override
    public List<Application> getApplicationsByUser(int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        // Truy vấn dựa trên thuộc tính candidateId (User) trong POJO Application
        Query q = s.createQuery("FROM Application a WHERE a.candidateId.id = :userId ORDER BY a.createdAt DESC", Application.class);
        q.setParameter("userId", userId);
        
        return q.getResultList();
    }
}