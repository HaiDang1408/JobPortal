package com.dht.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Gemini_AI_Refactor
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String EMPLOYER = "ROLE_EMPLOYER";
    public static final String CANDIDATE = "ROLE_CANDIDATE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role; // 'ROLE_ADMIN', 'ROLE_EMPLOYER', 'ROLE_CANDIDATE'

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "is_active")
    private Boolean isActive;

    // Kết nối tới các Đơn ứng tuyển (nếu là Candidate)
    @OneToMany(mappedBy = "candidateId")
    @JsonIgnore
    private Set<Application> applicationSet;

    // Kết nối tới Công ty (nếu là Employer)
    @OneToOne(mappedBy = "ownerId")
    @JsonIgnore
    private Company company;

    public User() {
        this.isActive = true;
    }

    public User(Integer id) {
        this.id = id;
    }

    // --- GETTERS AND SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Set<Application> getApplicationSet() { return applicationSet; }
    public void setApplicationSet(Set<Application> applicationSet) { this.applicationSet = applicationSet; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    @Override
    public String toString() {
        return "com.dht.pojo.User[ id=" + id + " - " + username + " ]";
    }
}