package com.dht.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Gemini_AI_Refactor
 */
@Entity
@Table(name = "applications")
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findByStatus", query = "SELECT a FROM Application a WHERE a.status = :status")
})
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content") // Lời nhắn gửi kèm đơn
    private String content;

    @Column(name = "cv_url") // Đường dẫn file CV đã nộp
    private String cvUrl;

    @Column(name = "status")
    private String status; // 'applied', 'viewed', 'rejected', 'shortlisted'

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JoinColumn(name = "job_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Job jobId;

    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User candidateId;

    public Application() {
        this.status = "applied";
        this.createdAt = new Date();
    }

    public Application(Integer id) {
        this.id = id;
    }

    // --- GETTERS AND SETTERS ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCvUrl() { return cvUrl; }
    public void setCvUrl(String cvUrl) { this.cvUrl = cvUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Job getJobId() { return jobId; }
    public void setJobId(Job jobId) { this.jobId = jobId; }

    public User getCandidateId() { return candidateId; }
    public void setCandidateId(User candidateId) { this.candidateId = candidateId; }

    @Override
    public String toString() {
        return "com.dht.pojo.Application[ id=" + id + " ]";
    }
}