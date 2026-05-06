package com.dht.repositories.impl;

import com.dht.pojo.Job;
import com.dht.repositories.JobRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@PropertySource("classpath:configs.properties")
@Transactional
public class JobRepositoryImpl implements JobRepository {

    @Autowired
    private Environment env;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Job> getJobs(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Job> q = b.createQuery(Job.class);
        Root root = q.from(Job.class);

        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            // Tìm theo tiêu đề
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("title"), String.format("%%%s%%", kw)));
            }

            // Tìm theo địa điểm
            String location = params.get("location");
            if (location != null && !location.isEmpty()) {
                predicates.add(b.like(root.get("location"), String.format("%%%s%%", location)));
            }

            // Tìm theo ngành nghề
            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(root.get("categoryId").get("id"), Integer.parseInt(cateId)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        q.orderBy(b.desc(root.get("createdAt")));

        Query query = s.createQuery(q);

        // Xử lý phân trang
        if (params != null) {
            String p = params.get("page");
            if (p != null && !p.isEmpty()) {
                int pageSize = Integer.parseInt(this.env.getProperty("job.pageSize", "10"));
                int start = (Integer.parseInt(p) - 1) * pageSize;
                query.setMaxResults(pageSize);
                query.setFirstResult(start);
            }
        }

        return query.getResultList();
    }

    @Override
    public void addOrUpdateJob(Job j) {
        Session s = this.factory.getObject().getCurrentSession();
        if (j.getId() == null) {
            s.persist(j);
        } else {
            s.merge(j);
        }
    }

    @Override
    public Job getJobById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Job.class, id);
    }

    @Override
    public void deleteJob(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Job j = this.getJobById(id);
        if (j != null) {
            s.remove(j);
        }
    }
    
    @Override
    public Long countJobs() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM Job");
        return (Long) q.getSingleResult();
    }
}