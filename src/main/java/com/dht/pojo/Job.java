package com.dht.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author
 */
@Entity
@Table(name = "jobs")
@NamedQueries({
    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j"),
    @NamedQuery(name = "Job.findById", query = "SELECT j FROM Job j WHERE j.id = :id"),
    @NamedQuery(name = "Job.findByTitle", query = "SELECT j FROM Job j WHERE j.title = :title"),
    @NamedQuery(name = "Job.findByLocation", query = "SELECT j FROM Job j WHERE j.location = :location"),
    @NamedQuery(name = "Job.findBySalaryMin", query = "SELECT j FROM Job j WHERE j.salaryMin = :salaryMin"),
    @NamedQuery(name = "Job.findBySalaryMax", query = "SELECT j FROM Job j WHERE j.salaryMax = :salaryMax"),
    @NamedQuery(name = "Job.findByCreatedAt", query = "SELECT j FROM Job j WHERE j.createdAt = :createdAt"),
    @NamedQuery(name = "Job.findByStatus", query = "SELECT j FROM Job j WHERE j.status = :status")})
@JsonIgnoreProperties(value = {
    "applicationSet", "savedJobSet"
})
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "benefits")
    private String benefits;

    @Column(name = "salary_min")
    private BigDecimal salaryMin;

    @Column(name = "salary_max")
    private BigDecimal salaryMax;

    @Column(name = "location")
    private String location;

    @Column(name = "deadline")
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Column(name = "status")
    private String status; // 'draft', 'active', 'expired'

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonProperty(value = "company")
    private Company companyId;

    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonProperty(value = "category")
    private Category categoryId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobId")
    private Set<Application> applicationSet;

    @Transient
    private MultipartFile file; // Dùng để upload ảnh/banner mô tả công việc (nếu có)

    public Job() {
        this.isFeatured = false;
        this.status = "active";
    }

    public Job(Integer id) {
        this.id = id;
    }

    public Job(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    // --- GETTERS AND SETTERS ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    public String getBenefits() { return benefits; }
    public void setBenefits(String benefits) { this.benefits = benefits; }

    public BigDecimal getSalaryMin() { return salaryMin; }
    public void setSalaryMin(BigDecimal salaryMin) { this.salaryMin = salaryMin; }

    public BigDecimal getSalaryMax() { return salaryMax; }
    public void setSalaryMax(BigDecimal salaryMax) { this.salaryMax = salaryMax; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getIsFeatured() { return isFeatured; }
    public void setIsFeatured(Boolean isFeatured) { this.isFeatured = isFeatured; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Company getCompanyId() { return companyId; }
    public void setCompanyId(Company companyId) { this.companyId = companyId; }

    public Category getCategoryId() { return categoryId; }
    public void setCategoryId(Category categoryId) { this.categoryId = categoryId; }

    public Set<Application> getApplicationSet() { return applicationSet; }
    public void setApplicationSet(Set<Application> applicationSet) { this.applicationSet = applicationSet; }

    public MultipartFile getFile() { return file; }
    public void setFile(MultipartFile file) { this.file = file; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Job)) return false;
        Job other = (Job) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.dht.pojo.Job[ id=" + id + " - " + title + " ]";
    }
}