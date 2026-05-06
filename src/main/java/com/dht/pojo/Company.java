package com.dht.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author 
 */
@Entity
@Table(name = "companies")
@NamedQueries({
    @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c"),
    @NamedQuery(name = "Company.findById", query = "SELECT c FROM Company c WHERE c.id = :id"),
    @NamedQuery(name = "Company.findByName", query = "SELECT c FROM Company c WHERE c.name = :name")
})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website;

    @Column(name = "logo")
    private String logo;

    @Column(name = "location")
    private String location;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @OneToOne(optional = false) // Một user thường chỉ quản lý một hồ sơ công ty
    private User ownerId;

    @OneToMany(mappedBy = "companyId", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Job> jobSet;

    public Company() {
        this.isVerified = false;
    }

    public Company(Integer id) {
        this.id = id;
    }

    // --- GETTERS AND SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Boolean getIsVerified() { return isVerified; }
    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }

    public User getOwnerId() { return ownerId; }
    public void setOwnerId(User ownerId) { this.ownerId = ownerId; }

    public Set<Job> getJobSet() { return jobSet; }
    public void setJobSet(Set<Job> jobSet) { this.jobSet = jobSet; }

    @Override
    public String toString() {
        return "com.dht.pojo.Company[ id=" + id + " - " + name + " ]";
    }
}