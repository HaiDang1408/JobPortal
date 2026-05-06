package com.dht.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private BigDecimal amount; // Số tiền giao dịch

    @Column(name = "payment_type")
    private String paymentType; // MOMO, PAYPAL, STRIPE

    @Column(name = "status")
    private String status; // PENDING, SUCCESS, FAILED

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId; // Người thực hiện thanh toán

    public Transaction() { this.createdAt = new Date(); }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public User getUserId() { return userId; }
    public void setUserId(User userId) { this.userId = userId; }
}
