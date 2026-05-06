package com.dht.repositories.impl;

import com.dht.pojo.User;
import com.dht.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Gemini_AI_Refactor
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query query = s.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null; // Trả về null thay vì văng lỗi để tầng Service xử lý
        }
    }

    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        // Mã hóa mật khẩu trước khi lưu vào database
        if (u.getPassword() != null && !u.getPassword().isEmpty()) {
            u.setPassword(this.passwordEncoder.encode(u.getPassword()));
        }
        s.persist(u);
        return u;
    }

    @Override
    public boolean authenticate(String username, String password) {
        User u = this.getUserByUsername(username);
        if (u != null) {
            return this.passwordEncoder.matches(password, u.getPassword());
        }
        return false;
    }

    @Override
    public boolean userExists(String username, String email) {
        Session s = this.factory.getObject().getCurrentSession();
        // Sử dụng query để đếm xem có user nào trùng username hoặc email không
        Query q = s.createQuery("SELECT COUNT(*) FROM User u WHERE u.username = :un OR u.email = :em");
        q.setParameter("un", username);
        q.setParameter("em", email);
        
        Long count = (Long) q.getSingleResult();
        return count > 0;
    }
}